package com.geektech.taskapp.onboard;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.geektech.taskapp.MainActivity;
import com.geektech.taskapp.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {


    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_board, container, false);
        int pos = getArguments().getInt("pos");
        TextView textView = view.findViewById(R.id.textView);
        ImageView imageView = view.findViewById(R.id.imageView);
        final Button button = view.findViewById(R.id.button);

        switch (pos) {
            case 0:
                button.setVisibility(View.INVISIBLE);
                textView.setText("Привет");
                imageView.setImageResource(R.drawable.onboard_page1);
                view.setBackgroundResource(R.color.colorFirst);
                break;

            case 1:
                button.setVisibility(View.INVISIBLE);
                textView.setText("Как дела?");
                imageView.setImageResource(R.drawable.onboard_page2);
                view.setBackgroundResource(R.color.colorSecond);
                break;

            case 2:
                textView.setText("Что делаешь?");
                button.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.onboard_page3);
                view.setBackgroundResource(R.color.colorThird);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
                        preferences.edit().putBoolean("isShown", true).apply();
                        startActivity(new Intent(getContext(), MainActivity.class));
                        getActivity().finish();
                    }
                });

                break;
        }

        return view;
    }

}
