package com.kpi.kovalenkodima.farmerhelper.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Field;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kovalenkodima on 12/7/14.
 */
public class PlantFieldsActivity extends ActionBarActivity {

    public static final String EXTRA_PLANT_ID = "plant_id";
    ListView listView;
    PlantFieldsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Plant Fields");
        setContentView(R.layout.act_plant_fields);

        Integer plantId = getIntent().getIntExtra(EXTRA_PLANT_ID,0);

        listView = (ListView) findViewById(R.id.list);
        adapter = new PlantFieldsAdapter(this,plantId);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(adapter);
    }


    private static class PlantFieldsAdapter extends BaseAdapter implements AdapterView.OnItemClickListener {

        Context context;
        LayoutInflater inflater;

        List<Field> allFields = new ArrayList<>();
        List<Field> myFields = new ArrayList<>();
        Integer plantId;

        public PlantFieldsAdapter(Context context, Integer plantId) {
            this.context = context;
            this.plantId = plantId;
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            List<Field> fields = DBHelper.getInstance(context).getAllFields();

            for (Field field : fields) {
                if (field.plantId.equals(-1))
                    allFields.add(field);
                else if (field.plantId.equals(plantId)) {
                    myFields.add(field);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == myFields.size() + 1) return 1;
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getCount() {
            return myFields.size() + allFields.size() + 2;
        }

        @Override
        public Field getItem(int position) {
            return (position > myFields.size()) ? allFields.get(position - myFields.size() - 2) : myFields.get(position - 1) ;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (getItemViewType(position) == 0) {
                if (convertView == null) {
                    convertView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);
                }

                ((TextView) convertView.findViewById(android.R.id.text1)).setText((getItem(position).name));
            } else {
                convertView = inflater.inflate(R.layout.plant_field_list_header,parent,false);
                ((TextView) convertView.findViewById(R.id.text1)).setText(position == 0 ? "Fields with this crop" : "Empty fields");
            }


            return convertView;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (getItemViewType(position) == 1) return;
            else {
                Field f= getItem(position);

                if (position > myFields.size()) {
                    allFields.remove(f);
                    myFields.add(f);
                    DBHelper.getInstance(context).plantCropsOnField(plantId,f.id);
                } else {
                    myFields.remove(f);
                    allFields.add(f);
                    DBHelper.getInstance(context).emptyField(f.id);
                }
                notifyDataSetChanged();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
