package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Qualification;

import java.util.List;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class QualificationsFragment extends Fragment {
    ListView listView;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_qualifications,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        listView.setAdapter(new QualificationsAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllQualifications()));

        view.findViewById(R.id.frag_qualifications_add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_new_qualification,null);
                final EditText nameEditText = (EditText) view.findViewById(R.id.qualification_name_edit_text);
                final EditText salaryEditText = (EditText) view.findViewById(R.id.qualification_salary_edit_text);
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.new_qualification)
                        .setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String name = nameEditText.getText().toString();
                                String salary = salaryEditText.getText().toString();
                                if (name.isEmpty() || salary.isEmpty()) {
                                    Toast.makeText(getActivity(), "Please, fill all fields!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Qualification q = new Qualification();
                                    q.name = name;
                                    q.salary = Integer.parseInt(salary);
                                    DBHelper.getInstance(getActivity()).insertQualification(q);
                                    listView.setAdapter(new QualificationsAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllQualifications()));

                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .show();
            }
        });
    }

    private static class QualificationsAdapter extends ArrayAdapter<Qualification> {

        LayoutInflater inflater;

        public QualificationsAdapter(Context context, List<Qualification> objects) {
            super(context,0 , objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_2,parent,false);
            }

            ((TextView) convertView.findViewById(android.R.id.text1)).setText(getItem(position).name);
            ((TextView) convertView.findViewById(android.R.id.text2)).setText(getItem(position).salary + "");
            return convertView;
        }
    }
}
