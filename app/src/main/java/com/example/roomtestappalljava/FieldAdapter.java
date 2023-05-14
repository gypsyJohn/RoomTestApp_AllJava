package com.example.roomtestappalljava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class FieldAdapter extends ListAdapter<FieldDBO,FieldViewHolder> {


    public FieldAdapter(@NonNull DiffUtil.ItemCallback<FieldDBO> diffCallback) {
        super(diffCallback);
    }

    @Override
    public FieldViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return FieldViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(FieldViewHolder holder, int position) {
        FieldDBO current = getItem(position);
        holder.bind(current.getFieldName());
    }

    static class FieldDiff extends DiffUtil.ItemCallback<FieldDBO> {

        @Override
        public boolean areItemsTheSame(@NonNull FieldDBO oldItem, @NonNull FieldDBO newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull FieldDBO oldItem, @NonNull FieldDBO newItem) {
            return oldItem.getFieldID() == newItem.getFieldID();
        }
    }

}

