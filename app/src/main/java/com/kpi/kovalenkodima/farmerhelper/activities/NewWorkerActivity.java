package com.kpi.kovalenkodima.farmerhelper.activities;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Worker;

/**
 * Created by kovalenkodima on 12/8/14.
 */
public class NewWorkerActivity extends ActionBarActivity {
    EditText nameEditText;
    ImageView photoImageView;
    EditText ageEditText;
    Spinner qualificationSpinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setContentView(R.layout.act_new_worker);
        nameEditText = (EditText) findViewById(R.id.act_new_worker_name);
        photoImageView = (ImageView) findViewById(R.id.act_new_worker_image);
        ageEditText = (EditText) findViewById(R.id.act_new_worker_age);
        qualificationSpinner = (Spinner) findViewById(R.id.act_new_worker_qualification_spinner);

        String [] from = {"QualificationName"};
        int [] to = {android.R.id.text1};
        qualificationSpinner.setAdapter(new SimpleCursorAdapter(this,android.R.layout.simple_spinner_dropdown_item, DBHelper.getInstance(this).getAllQualificationsCursor(),from,to));
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
            addNewWorker();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addNewWorker() {
        String name = nameEditText.getText().toString();
        String age = ageEditText.getText().toString();
        qualificationSpinner.getSelectedItem();
        Cursor c =((Cursor) qualificationSpinner.getSelectedItem());

        Integer qId = c.getInt(c.getColumnIndex("_id"));

        if (name.isEmpty() || age.isEmpty()) {
            Toast.makeText(this,"Please, fill all fields",Toast.LENGTH_SHORT).show();
        } else {
            Worker w = new Worker();
            w.age = Integer.parseInt(age);
            w.name = name;
            w.qualificationID = qId;

            DBHelper.getInstance(this).insertWorker(w);

            setResult(RESULT_OK);
            finish();
        }

    }


}
