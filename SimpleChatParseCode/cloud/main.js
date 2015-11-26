
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

Parse.Cloud.afterSave("Message", function(request) {
	var messageBody = request.object.get('body');
	var receiver = request.object.get('receiverId');
	var user = request.object.get('userId');

	var pushQuery = new Parse.Query(Parse.Installation);
	pushQuery.exists("user");
	pushQuery.include('user');
	pushQuery.equalTo("user", receiver);

	Parse.Push.send({
		where: pushQuery,
		data: {
			alert: user + ": " + messageBody,
			receiverId: user,
			body: messageBody
		}
	}, {
		success: function() {
			// Push was successful
		},
		error: function(error) {
			throw "Got an error " + error.code + " : " + error.message;
		}
	});
});
