package com.maddie.threadmanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.io.IOException;

public class ThreadMainFragment extends Fragment {

    private MainActivity theActivity;
    private Controller controller;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread_main, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.theActivity = (MainActivity)getActivity();
        this.controller = theActivity.getController();

        /**
        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ThreadMainFragment.this)
                        .navigate(R.id.action_ThreadMainFragment_to_ThreadEditFragment);
            }
        }); **/


        controller.setStockView(theActivity.findViewById(R.id.lvThreadList));
        controller.setListTitle(theActivity.findViewById(R.id.threadListTitle));
        controller.setThreadListView();
        controller.setSearchView();
    }
}