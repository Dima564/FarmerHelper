package com.kpi.kovalenkodima.farmerhelper.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class PlantDetailsActivity extends ActionBarActivity {
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
        fuelNeededTextView.setText("" + plant.technologicalMap.fuelNeeded + "l.");
        processingTimeTextView.setText("" + plant.technologicalMap.processingTime + " days");
    }
}
