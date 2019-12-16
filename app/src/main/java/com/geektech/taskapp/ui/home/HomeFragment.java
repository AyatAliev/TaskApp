package com.geektech.taskapp.ui.home;

import android.animation.Animator;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.geektech.taskapp.App;
import com.geektech.taskapp.FormActivity;
import com.geektech.taskapp.OnItemClickListener;
import com.geektech.taskapp.R;
import com.geektech.taskapp.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    private TaskAdapter adapter;
    private List<Task> list;
    AlertDialog.Builder ad;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_home, container, false);
        final RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));


        list = new ArrayList<>();
        App.getDatabase().taskDao().getAllLive().observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(final List<Task> tasks) {
                list.clear();
                list.addAll(tasks);
                Collections.reverse(list);
                adapter.notifyDataSetChanged();


            }
        });

        adapter = new TaskAdapter(list);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(int position) {
                SharedPreferences preferences = getActivity().getSharedPreferences("settings", MODE_PRIVATE);
                Task task = list.get(position);
                Intent intent = new Intent(getContext(), FormActivity.class);
                intent.putExtra("Task", task);
                startActivity(intent);
                Toast.makeText(getContext(), "pos = " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onItemLongClick(final int position) {

                ad = new AlertDialog.Builder(getActivity());
                ad.setTitle("Выберите действие");
                ad.setMessage("Вы точно хотите удалить?");
                ad.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        App.getDatabase().taskDao().delete(list.get(position));


                    }

                });
                ad.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();


                    }
                });

                ad.show();
            }

        });


        return root;
    }


    public void sortList() {
        list.clear();
        list.addAll(App.getDatabase().taskDao().sort());
        adapter.notifyDataSetChanged();
    }


}



    /*@Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            Task t = (Task) data.getSerializableExtra("key");
            list.add(0, t);
            adapter.notifyDataSetChanged();
        }
    }*/

