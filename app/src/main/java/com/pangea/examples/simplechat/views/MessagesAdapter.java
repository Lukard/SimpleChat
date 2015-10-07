package com.pangea.examples.simplechat.views;

import android.databinding.DataBindingUtil;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pangea.examples.simplechat.Message;
import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.ChatItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MessagesAdapter extends RecyclerViewAdapterBase<Message, ChatItemBinding> {

    public MessagesAdapter(List<Message> items) {
        this.items = items;
    }

    @Override
    protected ChatItemBinding onCreateItemView(ViewGroup parent, int viewType) {
        return DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.chat_item,
                parent,
                false);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<ChatItemBinding> holder, int position) {
        ChatItemBinding binding = (ChatItemBinding) holder.getBinding();
        Message message = items.get(position);

        binding.tvBody.setText(message.getBody());
        binding.ivProfileLeft.setVisibility(message.getIsMine() ? View.GONE : View.VISIBLE);
        binding.ivProfileRight.setVisibility(message.getIsMine() ? View.VISIBLE : View.GONE);

        if (message.getIsMine()) {
            binding.tvBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
            Picasso.with(binding.ivProfileRight.getContext())
                    .load(Message.getProfileUrl(message.getUserId()))
                    .into(binding.ivProfileRight);
        } else {
            binding.tvBody.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            Picasso.with(binding.ivProfileLeft.getContext())
                    .load(Message.getProfileUrl(message.getUserId()))
                    .into(binding.ivProfileLeft);
        }
    }

}
