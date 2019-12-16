package com.geektech.taskapp.ui.tools;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.airbnb.lottie.LottieAnimationView;
import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;

public class ToolsFragment extends Fragment {

    private ToolsViewModel toolsViewModel;
    private LottieAnimationView thumb_down;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_tools, container, false);

        thumb_down = root.findViewById(R.id.lav_thumbDown);
        thumb_down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thumb_down.setProgress(0);
                thumb_down.pauseAnimation();
                thumb_down.playAnimation();
                Toast.makeText(getContext(), "Boo!!", Toast.LENGTH_SHORT).show();

            }
        });
        thumb_down.playAnimation();
      //  thumb_down.setVisibility(View.GONE);
        thumb_down.addAnimatorUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animation.getCurrentPlayTime();
            }
        });
      //  thumb_down.setVisibility(View.INVISIBLE);

        return root;
    }
}