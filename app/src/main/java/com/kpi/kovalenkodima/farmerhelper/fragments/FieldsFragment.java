package com.kpi.kovalenkodima.farmerhelper.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.DBHelper;
import com.kpi.kovalenkodima.farmerhelper.R;
import com.kpi.kovalenkodima.farmerhelper.model.Field;

import java.util.List;

/**
 * Created by kovalenkodima on 12/5/14.
 */
public class FieldsFragment extends Fragment {
    FieldsAdapter adapter;
    ListView listView;
    ImageButton addFieldButton;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_fields,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.list);
        addFieldButton = (ImageButton) view.findViewById(R.id.frag_plants_add_btn);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle("Fields");

        if (adapter == null) {
            adapter = new FieldsAdapter(getActivity(), DBHelper.getInstance(getActivity()).getAllFields());
        }
        listView.setAdapter(adapter);

        addFieldButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
    }


    private void showAddDialog() {

        View view = View.inflate(getActivity(),R.layout.new_field_dialog,null);
        final EditText nameEditText = (EditText) view.findViewById(R.id.field_name_edit_text);
        final EditText addressEditText = (EditText) view.findViewById(R.id.field_address_edit_text);

        new AlertDialog.Builder(getActivity())
                .setTitle(R.string.new_field_dialog_title)
                .setView(view)
                .setPositiveButton(R.string.add_field, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String name = nameEditText.getText().toString();
                        String address = addressEditText.getText().toString();

                        if (!name.isEmpty() && !address.isEmpty()) {
                            DBHelper.getInstance(getActivity()).insertField(new Field(name,address,-1));
                        }
                        listView.setAdapter(new FieldsAdapter(getActivity(),
                                DBHelper.getInstance(getActivity()).getAllFields()));

                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}
                }).create().show();
    }

    private static class FieldsAdapter extends ArrayAdapter<Field> {

        LayoutInflater inflater;

        public FieldsAdapter(Context context, List<Field> objects) {
            super(context, 0, objects);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.fields_list_item,parent,false);
            }

            Field field = getItem(position);
            ((TextView) convertView.findViewById(R.id.frag_field_list_item_title))
                    .setText(field.name);
            ((TextView) convertView.findViewById(R.id.frag_field_list_item_sub_title))
                    .setText(field.address);

            return convertView;
        }
    }



}
