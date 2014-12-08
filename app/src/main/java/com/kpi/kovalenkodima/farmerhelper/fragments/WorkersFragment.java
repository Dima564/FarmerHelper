package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.activities.NewWorkerActivity;
import com.kpi.kovalenkodima.farmerhelper.model.Worker;

import java.util.List;

/**
 * Created by kovalenkodima on 12/5/14.
 */
public class WorkersFragment extends android.support.v4.app.Fragment {

    WorkersAdapter adapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_workers,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        if (adapter == null)
            adapter = new WorkersAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllWorkers());
        listView.setAdapter(adapter);

        view.findViewById(R.id.frag_workers_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(),NewWorkerActivity.class);
                startActivityForResult(i, 1032);
            }
        });

    }

    private static class WorkersAdapter extends ArrayAdapter<Worker> {
        LayoutInflater inflater;
        public WorkersAdapter(Context context, List<Worker> objects) {
            super(context, 0, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.workers_list_item,parent,false);
            }

            ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).name);
            ((TextView) convertView.findViewById(R.id.text2)).setText(getItem(position).age + "");
            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1032 && resultCode == Activity.RESULT_OK) {
            if (adapter == null)
                adapter = new WorkersAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllWorkers());
            listView.setAdapter(adapter);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
