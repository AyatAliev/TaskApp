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
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.geektech.taskapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;
    private List<String> urls;
    private RecyclerView recyclerView;
    private SFAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        recyclerView = root.findViewById(R.id.fs_recyclerView);

            initList();
        return root;
    }

    private void initList() {
        urls = new ArrayList<>();
        urls.add("https://images.freeimages.com/images/small-previews/05e/on-the-road-6-1384796.jpg");
        urls.add("https://www.bensound.com/bensound-img/november.jpg");
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
     //   adapter = new SFAdapter(urls);
        recyclerView.setAdapter(adapter);
    }
}