package com.kpi.kovalenkodima.farmerhelper.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.Utils;
import com.kpi.kovalenkodima.farmerhelper.fragments.QualificationPickerDialogFragment;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;
import com.kpi.kovalenkodima.farmerhelper.model.Qualification;

import java.util.List;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class PlantDetailsActivity extends ActionBarActivity {

    QualificationAdapter adapter;
    public static final String EXTRA_PLANT_ID = "extra_plant_id";

    ImageView plantImageView;
    TextView plantNameTextView;
    TextView technologyNameTextView;
    TextView technologyMonthTextView;
    TextView fuelNeededTextView;
    TextView processingTimeTextView;
    ListView qualificationsListView;


    Plant plant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Plant Details");
        setContentView(R.layout.act_plant_details);
        Integer plantId = getIntent().getIntExtra(EXTRA_PLANT_ID,0);

        plantImageView = (ImageView) findViewById(R.id.act_plant_details_plant_image);
        plantNameTextView = (TextView) findViewById(R.id.act_plant_details_plant_name);
        technologyNameTextView = (TextView) findViewById(R.id.act_plant_details_technology_name);
        technologyMonthTextView = (TextView) findViewById(R.id.act_plant_details_technology_month);
        fuelNeededTextView = (TextView) findViewById(R.id.act_plant_details_technology_fuel);
        processingTimeTextView = (TextView) findViewById(R.id.act_plant_details_technology_processing_time);
        qualificationsListView = (ListView) findViewById(R.id.act_plant_details_qualification_list);

        plant = DBHelper.getInstance(this).getPlantById(plantId);

        plantNameTextView.setText(plant.name);
        technologyNameTextView.setText(plant.technologicalMap.name);
        technologyMonthTextView.setText(plant.technologicalMap.month + "");
        fuelNeededTextView.setText("" + plant.technologicalMap.fuelNeeded + " l.");
        processingTimeTextView.setText("" + plant.technologicalMap.processingTime + " days");

        findViewById(R.id.act_plant_details_add_qualification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dialogFragment = new QualificationPickerDialogFragment();
                dialogFragment.show(getSupportFragmentManager(),"qualification_picker");
            }
        });

        qualificationsListView.setAdapter(new QualificationAdapter(this,DBHelper.getInstance(this).getQualificationsForTechnology(plant.technologicalMap.id)));
    }

    public void addNewQualification(Qualification q) {
        DBHelper.getInstance(getApplicationContext()).insertQualificationRequirement(plant.technologicalMap.id,q.id);

        qualificationsListView.setAdapter(new QualificationAdapter(this,DBHelper.getInstance(this).getQualificationsForTechnology(plant.technologicalMap.id)));
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Utils.navigateUp(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class QualificationAdapter extends ArrayAdapter<Qualification> {

        LayoutInflater inflater;
        List<Qualification> qualifications;

        @Override
        public Qualification getItem(int position) {
            return qualifications.get(position);
        }

        @Override
        public int getCount() {
            return qualifications.size();
        }

        public QualificationAdapter(Context context, List<Qualification> objects) {
            super(context, 0);
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            qualifications = objects;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.act_plant_details_qualification_list_item,parent,false);
            }
            ((TextView) convertView.findViewById(R.id.text1)).setText(getItem(position).name);
            convertView.findViewById(R.id.remove_button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBHelper.getInstance(getContext()).removeQualificationRequirement(plant.technologicalMap.id,getItem(position).id);
                    qualifications.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}
