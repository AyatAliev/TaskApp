package com.geektech.taskapp.ui.slideshow;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;

import java.util.List;

public class SFAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<ImageView> list;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ImageView ImageView = (android.widget.ImageView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fs_viewholder, parent, false);
        return new ViewHolder(ImageView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(list.get(position));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}

class ViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.fs_ImageView);
    }

    public void bind(ImageView imageView){

    }


}
