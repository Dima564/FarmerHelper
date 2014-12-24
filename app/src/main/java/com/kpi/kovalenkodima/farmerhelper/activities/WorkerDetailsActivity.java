package com.kpi.kovalenkodima.farmerhelper.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.R;

/**
 * Created by kovalenkodima on 12/8/14.
 */
public class WorkerDetailsActivity extends ActionBarActivity {

    TextView headerNameEditText;
    TextView fieldNameEditText;
    TextView ageEditText;
    TextView qualificationEditText;
    TextView salaryEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.act_worker_details);
    }
}
