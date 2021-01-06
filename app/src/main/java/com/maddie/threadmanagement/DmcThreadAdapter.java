package com.maddie.threadmanagement;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import java.util.List;

public class DmcThreadAdapter extends BaseAdapter {
    private List<DmcThread> itemList;
    private LayoutInflater inflater;
    private Activity context;

    public DmcThreadAdapter(Activity context, List<DmcThread> threads) {
        super();
        this.context = context;
        this.itemList = threads;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //abstract methods used by parent - except getItemID?
    public int getCount() { return itemList.size(); }
    public Object getItem(int position) { return itemList.get(position); }
    public long getItemId(int position) { return position; }


    //convenient store to be used with tagging
    public static class ViewHolder {
        View threadColorView;
        TextView threadNameTextView;
        ImageButton updateThreadBtn;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            //no previous view, instantiate holder, inflate view and setup views of holder
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.thread_item, null);

            holder.threadColorView = (View) convertView.findViewById(R.id.threadColorView);
            holder.threadNameTextView = (TextView) convertView.findViewById(R.id.threadNameTextView);
            holder.updateThreadBtn = (ImageButton) convertView.findViewById(R.id.updateThreadBtn);

            //Remember the holder
            convertView.setTag(holder);
        } else {
            //Get holder to prepare for update with new data
            holder = (ViewHolder) convertView.getTag();
        }

        //Update views
        DmcThread thread = (DmcThread) itemList.get(position);
        holder.threadColorView.setBackgroundColor(Color.parseColor(thread.getHexColor()));
        holder.threadNameTextView.setText(thread.toString());
        holder.updateThreadBtn.setOnClickListener(view -> {

            Fragment fragment = FragmentManager.findFragment(view);
            DmcThreadViewModel viewModel = new ViewModelProvider(fragment.requireActivity()).get(DmcThreadViewModel.class);

            viewModel.selectItem(thread);

            NavHostFragment.findNavController(fragment)
                    .navigate(R.id.action_ThreadMainFragment_to_ThreadEditFragment);
        });

        return convertView;
    }
}
