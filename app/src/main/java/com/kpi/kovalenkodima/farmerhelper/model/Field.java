package com.kpi.kovalenkodima.farmerhelper.model;

import android.database.Cursor;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class Field {
    public String name;
    public String address;
    public Integer plantId;

    public Field(String name, String address, Integer plantId) {
        this.name = name;
        this.address = address;
        this.plantId = plantId;
    }

    public static Field fromCursor(Cursor c) {
        return new Field(
                c.getString(c.getColumnIndex("Name")),
                c.getString(c.getColumnIndex("Address")),
                c.getInt(c.getColumnIndex("fk_Plant")));
    }


}
