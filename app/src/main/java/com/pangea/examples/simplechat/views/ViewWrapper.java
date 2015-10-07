package com.pangea.examples.simplechat.views;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;

public class ViewWrapper<V extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private ViewDataBinding binding;

    public ViewWrapper(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public ViewDataBinding getBinding() {
        return binding;
    }
}
