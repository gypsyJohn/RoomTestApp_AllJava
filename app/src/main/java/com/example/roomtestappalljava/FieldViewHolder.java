package com.example.roomtestappalljava;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class FieldViewHolder extends RecyclerView.ViewHolder {

    private final TextView fieldItemView;

    private FieldViewHolder(View itemView) {
        super(itemView);
        fieldItemView = itemView.findViewById(R.id.textView);
    }

    public void bind(String text) {
        fieldItemView.setText(text);
    }

    static FieldViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new FieldViewHolder(view);
    }
}
