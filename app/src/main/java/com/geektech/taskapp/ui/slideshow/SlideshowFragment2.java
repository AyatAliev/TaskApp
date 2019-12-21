package com.geektech.taskapp.ui.slideshow;

import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment2 extends Fragment implements View.OnClickListener {

    private SlideshowViewModel slideshowViewModel;
    private List<String> urls;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private SFAdapter adapter;
    private List<File> list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        root.findViewById(R.id.btnDownload).setOnClickListener(this);
        recyclerView = root.findViewById(R.id.fs_recyclerView);


        progressBar = root.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        urls = new ArrayList<>();
        urls.add("https://images.freeimages.com/images/small-previews/05e/on-the-road-6-1384796.jpg");
        urls.add("https://www.bensound.com/bensound-img/november.jpg");

            initList();
        return root;
    }

    private void initList() {
        list = new ArrayList<>();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new SFAdapter(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        progressBar.setVisibility(View.VISIBLE);
        File folder = new File(Environment.getExternalStorageDirectory(), "TaskApp/Images");
        folder.mkdirs();
        downloadFiles(folder);
    }

    private void downloadFiles(final File folder) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    for (int i = 0; i < urls.size(); i++) {
                        String url = urls.get(i);
                        String fileName = url.substring(url.lastIndexOf("/")+1);
                        File file = new File(folder, fileName); //TODO
                        FileUtils.copyURLToFile(new URL(url), file);
                        list.add(file);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(View.GONE);
                        adapter.notifyDataSetChanged();

                    }
                });
            }

        });
        thread.start();
    }
}