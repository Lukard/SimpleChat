package com.pangea.examples.simplechat.views;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import com.pangea.examples.simplechat.R;
import com.pangea.examples.simplechat.databinding.UserItemBinding;
import com.pangea.examples.simplechat.viewModel.UserViewModel;
import com.parse.ParseUser;

import java.util.List;

public class UsersAdapter extends RecyclerViewAdapterBase<ParseUser, UserItemBinding> implements View.OnClickListener {

    private OnUserClickListener listener;

    public UsersAdapter(List<ParseUser> items, OnUserClickListener listener) {
        this.items = items;
        this.listener = listener;
    }

    @Override
    protected UserItemBinding onCreateItemView(ViewGroup parent, int viewType) {
         return DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.user_item,
                parent,
                false);
    }

    @Override
    public void onBindViewHolder(ViewWrapper<UserItemBinding> holder, int position) {
        final UserItemBinding binding = (UserItemBinding) holder.getBinding();
        ParseUser user = items.get(position);

        UserViewModel userViewModel = new UserViewModel(user);

        binding.setUser(userViewModel);
        binding.getRoot().setTag(position);
        binding.getRoot().setOnClickListener(this);

        binding.ivProfile.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                binding.ivProfile.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                int width = binding.ivProfile.getWidth();
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) binding.ivProfile.getLayoutParams();
                params.height = width;
                binding.ivProfile.setLayoutParams(params);
            }
        });
    }

    @Override
    public void onClick(View v) {
        listener.onUserClick(items.get((Integer) v.getTag()));
    }

    public interface OnUserClickListener {
        void onUserClick(ParseUser user);
    }
}
