package com.kpi.kovalenkodima.farmerhelper.model;

import android.database.Cursor;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class Plant {
    public String name;
    public TechnologicalMap technologicalMap;

    public static Plant fromCursor(Cursor c) {
        TechnologicalMap map = new TechnologicalMap();
        map.name = c.getString(c.getColumnIndex("Name"));
        map.month = c.getInt(c.getColumnIndex("Month"));
        map.processingTime = c.getInt(c.getColumnIndex("ProcessingTime"));
        map.fuelNeeded = c.getInt(c.getColumnIndex("FuelNeeded"));

        Plant plant = new Plant();
        plant.technologicalMap = map;
        plant.name = c.getString(c.getColumnIndex("Name"));

        return plant;
    }
}
