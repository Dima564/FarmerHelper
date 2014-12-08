package com.kpi.kovalenkodima.farmerhelper.model;

import android.database.Cursor;

/**
 * Created by kovalenkodima on 12/8/14.
 */
public class Worker {
    public Integer id;
    public String name;
    public Integer age;
    public Integer qualificationID;

    public static Worker fromCursor(Cursor c) {
        Worker w = new Worker();
        w.id = c.getInt(c.getColumnIndex("WorkerId"));
        w.age = c.getInt(c.getColumnIndex("WorkerAge"));
        w.name = c.getString(c.getColumnIndex("WorkerName"));
        return w;
    }
}
