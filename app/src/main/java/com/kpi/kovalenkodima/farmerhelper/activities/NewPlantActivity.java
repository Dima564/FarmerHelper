package com.kpi.kovalenkodima.farmerhelper.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;
import com.kpi.kovalenkodima.farmerhelper.model.TechnologicalMap;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class NewPlantActivity extends ActionBarActivity {

    EditText plantEditText;
    EditText technologyNameEditText;
    Spinner monthSpinner;
    EditText fuelEditText;
    EditText timeEditText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_plant);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        plantEditText = (EditText) findViewById(R.id.act_new_plant_name);
        technologyNameEditText = (EditText) findViewById(R.id.act_new_plant_technology_name);
        fuelEditText = (EditText) findViewById(R.id.act_new_plant_fuel);
        timeEditText = (EditText) findViewById(R.id.act_new_plant_processing_time);
        monthSpinner = (Spinner) findViewById(R.id.act_new_worker_qualification_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                if (position == getCount()) {
                    ((TextView) v.findViewById(android.R.id.text1)).setText("");
                    ((TextView) v.findViewById(android.R.id.text1)).setHint(getItem(getCount()));
                }
                return v;
            }

            @Override
            public int getCount() {
                return super.getCount() - 1;
            }
        };

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        for (int i = 1; i <= 12; i++)
            adapter.add("" + i);
        adapter.add("Month");

        monthSpinner.setAdapter(adapter);
        monthSpinner.setSelection(adapter.getCount());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.act_new_plant,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.act_new_plant_done) {
            addNewPlant();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewPlant() {

        String plantName = plantEditText.getText().toString();
        String technologyName = technologyNameEditText.getText().toString();
        Integer month = monthSpinner.getSelectedItemPosition() + 1;
        String  time = timeEditText.getText().toString();
        String fuel = fuelEditText.getText().toString();

        if (plantName.isEmpty() ||
                technologyName.isEmpty() ||
                month > 12 ||
                time.isEmpty() ||
                fuel.isEmpty()) {
            Toast.makeText(this,"Please, fill all the fields",Toast.LENGTH_SHORT).show();
        } else {
            TechnologicalMap map = new TechnologicalMap();
            map.name = technologyName;
            map.processingTime = Integer.parseInt(time);
            map.fuelNeeded = Integer.parseInt(fuel);
            map.month = month;

            Plant plant = new Plant();
            plant.name = plantName;
            plant.technologicalMap = map;

            DBHelper.getInstance(this).insertPlant(plant);
            setResult(RESULT_OK);
            finish();
        }
    }
}
