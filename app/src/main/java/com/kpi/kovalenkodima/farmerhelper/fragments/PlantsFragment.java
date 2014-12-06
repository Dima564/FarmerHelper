package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.activities.PlantDetailsActivity;
import com.kpi.kovalenkodima.farmerhelper.activities.NewPlantActivity;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;

import java.util.List;

/**
 * Created by kovalenkodima on 12/5/14.
 */
public class PlantsFragment extends android.support.v4.app.Fragment {
    private static final int REQUEST_NEW_PLANT = 1015;
    ListView list;
    PlantsAdapter adapter;
    ImageButton newPlantButton;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_plants,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        list = (ListView) view.findViewById(R.id.list);
        newPlantButton = (ImageButton) view.findViewById(R.id.frag_plants_add_btn);

        newPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), NewPlantActivity.class);
                startActivityForResult(i,REQUEST_NEW_PLANT);
            }
        });

        // TODO use asynctask
        if (adapter == null) {
            adapter = new PlantsAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllPlants());
        }
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getItem(position);
                Intent i = new Intent(getActivity(),PlantDetailsActivity.class);
                i.putExtra(PlantDetailsActivity.EXTRA_PLANT_ID, adapter.getItem(position).id);
                startActivity(i);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Plants");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_NEW_PLANT && resultCode == Activity.RESULT_OK) {
            adapter = new PlantsAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllPlants());
            list.setAdapter(adapter);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static class PlantsAdapter extends ArrayAdapter<Plant> {
        LayoutInflater inflater;

        public PlantsAdapter(Context context, List<Plant> objects) {
            super(context, 0, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.frag_plants_list_item, parent, false);
            }

            ((TextView) convertView.findViewById(R.id.text)).setText(getItem(position).name);

            return convertView;
        }


    }
}
