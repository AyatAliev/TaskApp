package com.geektech.taskapp.ui.slideshow;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SFAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<File> list;

    public SFAdapter(List<File> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fs_viewholder, parent, false);
        return new ViewHolder(v);
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

    public void bind(File file) {
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        imageView.setImageBitmap(bitmap);

    }


}
