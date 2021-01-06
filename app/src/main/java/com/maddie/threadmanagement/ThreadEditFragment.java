package com.maddie.threadmanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

public class ThreadEditFragment extends Fragment {

    private MainActivity theActivity;
    private Controller controller;
    private DmcThreadViewModel viewModel;
    private DmcThread thread;
    private TextView dmc;
    private View color;
    private TextView hex;
    private TextView name;
    private Switch stockSwitch;
    private Switch lowSwitch;
    private Switch needSwitch;
    private TextView qty;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_thread_edit, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.theActivity = (MainActivity)getActivity();
        this.controller = theActivity.getController();

        this.viewModel = new ViewModelProvider(theActivity).get(DmcThreadViewModel.class);
        viewModel.getSelectedItem().observe(theActivity, item -> {
            this.thread = item;
        });

        setAllDisplayableFields();

        setSwitches();

        view.findViewById(R.id.saveThreadEditFab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //saveAll();

                NavHostFragment.findNavController(ThreadEditFragment.this)
                        .navigate(R.id.action_ThreadEditFragment_to_ThreadMainFragment);
            }
        });
    }

    public void setAllDisplayableFields() {
        this.dmc = theActivity.findViewById(R.id.editThreadDmc);
        this.color = theActivity.findViewById(R.id.editThreadColorView);
        this.hex = theActivity.findViewById(R.id.editThreadHex);
        this.name = theActivity.findViewById(R.id.editThreadName);
        this.stockSwitch = (Switch)theActivity.findViewById(R.id.stockSwitch);
        this.lowSwitch = (Switch)theActivity.findViewById(R.id.lowSwitch);
        this.needSwitch = (Switch)theActivity.findViewById(R.id.needSwitch);
        this.qty = theActivity.findViewById(R.id.editThreadQtyDisplay);

        dmc.setText(thread.getDmc());
        color.setBackgroundColor(Color.parseColor(thread.getHexColor()));
        hex.setText(thread.getHexColor());
        name.setText(thread.getName());
        stockSwitch.setChecked(thread.isInStock());
        lowSwitch.setChecked(thread.isLowStock());
        needSwitch.setChecked(thread.need());
        qty.setText(String.format("%d", thread.getSkeinQty()));
    }

    public void saveStockStatus() {
        if (stockSwitch.isChecked()) {
            thread.addToStock();
        } else {
            thread.removeFromStock();
        }
    }

    public void saveLowStatus() {
        if (lowSwitch.isChecked()) {
            thread.setLowStock(true);
        } else {
            thread.setLowStock(false);
        }
    }

    public void saveNeedStatus() {
        if (needSwitch.isChecked()) {
            thread.setNeed(true);
        } else {
            thread.setNeed(false);
        }
    }

    public void setSwitches() {

        stockSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveStockStatus();

            lowSwitch.setChecked(thread.isLowStock());
            needSwitch.setChecked(thread.need());
            qty.setText(String.format("%d", thread.getSkeinQty()));
        });

        lowSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveLowStatus();

            stockSwitch.setChecked(thread.isInStock());
            needSwitch.setChecked(thread.need());
            qty.setText(String.format("%d", thread.getSkeinQty()));
        });

        needSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            saveNeedStatus();
            stockSwitch.setChecked(thread.isInStock());
            lowSwitch.setChecked(thread.isLowStock());
            qty.setText(String.format("%d", thread.getSkeinQty()));
        });

    }
}