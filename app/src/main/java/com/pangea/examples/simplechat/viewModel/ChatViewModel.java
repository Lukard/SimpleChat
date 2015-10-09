package com.pangea.examples.simplechat.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.view.View;

import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.parse.UserUtils;
import com.parse.ParseException;
import com.parse.SaveCallback;

public class ChatViewModel extends BaseObservable implements View.OnClickListener {

    private String newMessageText;

    @Bindable
    public String getNewMessageText() {
        return newMessageText;
    }

    public void setNewMessageText(String newMessageText) {
        this.newMessageText = newMessageText;
    }

    public View.OnClickListener onClickSend() {
        return this;
    }

    @Override
    public void onClick(View v) {
        // Use Message model to create new messages now
        Message message = new Message();
        message.setUserId(UserUtils.getMyUserId());
        message.setBody(newMessageText);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
//                receiveMessage();
            }
        });
        setNewMessageText("");
    }
}
