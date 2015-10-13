package com.pangea.examples.simplechat.viewModel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.pangea.examples.simplechat.BR;
import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.parse.UserUtils;
import com.parse.SaveCallback;

public class ChatViewModel extends BaseObservable implements View.OnClickListener, TextWatcher {

    private String newMessageText;
    private String receiverId;
    private SaveCallback receiveMessageCallback;

    public ChatViewModel(String receiverId, SaveCallback callback) {
        this.receiverId = receiverId;
        this.receiveMessageCallback = callback;
    }

    @Bindable
    public String getNewMessageText() {
        return newMessageText;
    }

    public void setNewMessageText(String newMessageText, boolean mustNotify) {
        this.newMessageText = newMessageText;
        if (mustNotify) notifyPropertyChanged(BR.newMessageText);
    }

    public View.OnClickListener onClickSend() {
        return this;
    }

    @Override
    public void onClick(View v) {
        // Use Message model to create new messages now
        Message message = new Message();
        message.setUserId(UserUtils.getMyUserId());
        message.setReceiverId(receiverId);
        message.setBody(getNewMessageText());
        message.saveInBackground(receiveMessageCallback);
        setNewMessageText("", true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String text = s.toString();
        if (!text.equals(getNewMessageText())) {
            setNewMessageText(text, false);
        }
    }
}
