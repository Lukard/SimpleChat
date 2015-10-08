package com.pangea.examples.simplechat.views;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.pangea.examples.simplechat.model.Message;
import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.ChatItemBinding;
import com.pangea.examples.simplechat.viewModel.MessageViewModel;
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
        MessageViewModel messageViewModel = new MessageViewModel(message);

        if (messageViewModel.getIsMine()) {
            Picasso.with(binding.ivProfileRight.getContext())
                    .load(MessageViewModel.getProfileUrl(message.getUserId()))
                    .into(binding.ivProfileRight);
        } else {
            Picasso.with(binding.ivProfileLeft.getContext())
                    .load(MessageViewModel.getProfileUrl(message.getUserId()))
                    .into(binding.ivProfileLeft);
        }

        binding.setMessage(messageViewModel);
    }

}
