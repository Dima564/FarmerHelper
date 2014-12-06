package com.kpi.kovalenkodima.farmerhelper.model;

import android.database.Cursor;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class Plant {
    public Integer id;
    public String name;
    public TechnologicalMap technologicalMap;

    public static Plant fromCursor(Cursor c) {
        TechnologicalMap map = new TechnologicalMap();
        map.id = c.getInt(c.getColumnIndex("TechnologyId"));
        map.name = c.getString(c.getColumnIndex("TechnologyName"));
        map.month = c.getInt(c.getColumnIndex("TechnologyMonth"));
        map.processingTime = c.getInt(c.getColumnIndex("TechnologyProcessingTime"));
        map.fuelNeeded = c.getInt(c.getColumnIndex("TechnologyFuelNeeded"));

        Plant plant = new Plant();
        plant.technologicalMap = map;
        plant.id = c.getInt(c.getColumnIndex("PlantId"));
        plant.name = c.getString(c.getColumnIndex("PlantName"));

        return plant;
    }
}
