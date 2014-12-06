package com.kpi.kovalenkodima.farmerhelper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.kpi.kovalenkodima.farmerhelper.model.Field;

import java.util.List;

/**
 * Created by kovalenkodima on 12/5/14.
 */
public class FieldsFragment extends ListFragment {
    FieldsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (adapter == null) {
            adapter = new FieldsAdapter(getActivity(),DBHelper.getInstance(getActivity()).getAllFields());
        }
        setListAdapter(adapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.frag_fields,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.frag_field_list_add_item_menu) {
            showAddDialog();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
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
                            DBHelper.getInstance(getActivity()).insertField(new Field(name,address,null));
                        }
                        setListAdapter(new FieldsAdapter(getActivity(),
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
