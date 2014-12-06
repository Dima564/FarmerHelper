package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.activities.PlantDetailsActivity;
import com.kpi.kovalenkodima.farmerhelper.model.Qualification;

import java.util.List;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class QualificationPickerDialogFragment extends DialogFragment {
    ListView listView;
    QualificationsAdapter adapter;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view =  getActivity().getLayoutInflater().inflate(R.layout.frag_qualification_picker,null);
        listView = (ListView) view.findViewById(R.id.list);

        if (adapter == null) {
            List<Qualification> qs  = DBHelper.getInstance(getActivity()).getAllQualifications();
            adapter = new QualificationsAdapter(getActivity(),qs);
        }

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((PlantDetailsActivity) getActivity()).addNewQualification(adapter.getItem(position));
                QualificationPickerDialogFragment.this.dismiss();
            }
        });
        return new AlertDialog.Builder(getActivity()).setTitle("Select Qualification").setView(view).create();
    }

    private static class QualificationsAdapter extends ArrayAdapter<Qualification> {

        LayoutInflater inflater;
        public QualificationsAdapter(Context context, List<Qualification> objects) {
            super(context, 0, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                convertView = inflater.inflate(android.R.layout.simple_list_item_1,parent,false);

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).name);
            return convertView;
        }
    }
}
