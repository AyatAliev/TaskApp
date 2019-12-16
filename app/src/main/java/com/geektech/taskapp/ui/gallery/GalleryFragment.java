package com.geektech.taskapp.ui.gallery;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.geektech.taskapp.R;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import pub.devrel.easypermissions.EasyPermissions;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;
    Button button;
    ProgressBar progressBar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        button = root.findViewById(R.id.fg_download);
        progressBar = root.findViewById(R.id.progress_bar);
        initFile();
        return root;
    }

    private void initFile() {

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.INVISIBLE);

                if (EasyPermissions.hasPermissions(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                    File downloadImages = new File(Environment.getExternalStorageDirectory(), "TaskApp/Images");
                    downloadImages.mkdirs();

                    ArrayList<File> images = new ArrayList<>();
                    images.add(new File(downloadImages, "image1.png"));
                    images.add(new File(downloadImages, "image2.png"));
                    images.add(new File(downloadImages, "image3.png"));
                    images.add(new File(downloadImages, "image4.png"));
                    images.add(new File(downloadImages, "image5.png"));

                    downloadFile(images);





                } else {
                    EasyPermissions.requestPermissions(getActivity(), "Разрешить?", 101,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE);



                }

                }
            });
        }

        private void downloadFile(final ArrayList<File> image){

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<URL> urls = new ArrayList<>();
                try {
                    URL image1 = new URL ("https://images.freeimages.com/images/small-previews/05e/on-the-road-6-1384796.jpg");
                    urls.add(image1);
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    URL image2 = new URL ("https://www.bensound.com/bensound-img/november.jpg");
                    urls.add(image2);
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    URL image3 = new URL ("https://cdn.pixabay.com/photo/2019/11/25/09/00/exotic-4651348__340.jpg");
                    urls.add(image3);
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    URL image4 = new URL ("https://www.gettyimages.co.uk/gi-resources/images/RoyaltyFree/Apr17Update/ColourSurge1.jpg");
                    urls.add(image4);
                }catch (IOException e){
                    e.printStackTrace();
                }

                try {
                    URL image5 = new URL ("https://cdn.pixabay.com/photo/2019/11/19/14/11/landscape-4637538__340.jpg");
                    urls.add(image5);
                }catch (IOException e){
                    e.printStackTrace();
                }

                for (int i = 0; i < image.size(); i++) {

                    try {
                        FileUtils.copyURLToFile(urls.get(i), image.get(i));

                    }catch (IOException e){
                        e.printStackTrace();
                    }
                }

                progressBar.setVisibility(View.INVISIBLE);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        button.setVisibility(View.VISIBLE);

                    }
                });




            }

        });


        thread.start();

        }


    }