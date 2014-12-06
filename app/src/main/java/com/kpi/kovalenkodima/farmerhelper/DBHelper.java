package com.kpi.kovalenkodima.farmerhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kpi.kovalenkodima.farmerhelper.model.Field;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "kpi.farmer.db";
    private static final int DATABASE_VERSION = 1;

    private static DBHelper INSTANCE;

    private Context context;

    public static DBHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (DBHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DBHelper(context,DATABASE_NAME,null,DATABASE_VERSION);
                }
            }
        }
        return INSTANCE;
    }


    private DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(context.getResources().getString(R.string.sql_create_table_fields));
        db.execSQL(context.getResources().getString(R.string.sql_create_table_plants));
        db.execSQL(context.getResources().getString(R.string.sql_create_table_technology_map));
        db.execSQL(context.getResources().getString(R.string.sql_create_table_worker));
        db.execSQL(context.getResources().getString(R.string.sql_create_table_requires_qualification));
        db.execSQL(context.getResources().getString(R.string.sql_create_table_qualification));
    }

    public void insertPlant(Plant plant) {

    }

    public void insertField(Field field) {
        ContentValues cv = new ContentValues(3);
        cv.put("Name",field.name);
        cv.put("Address", field.address);
        cv.put("fk_Plant", field.plantId);

        getWritableDatabase().insert("Field",null,cv);
    }

    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        Cursor c = getReadableDatabase().query("Field",null,null,null,null,null,null);
        if (c.moveToFirst()) {
            do {
                fields.add(Field.fromCursor(c));
            } while (c.moveToNext());
        }
        return fields;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
