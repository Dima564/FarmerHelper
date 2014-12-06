package com.kpi.kovalenkodima.farmerhelper.model;

import android.database.Cursor;

/**
 * Created by kovalenkodima on 12/6/14.
 */
public class Qualification {
    public Integer id;
    public String name;
    public Integer salary;

    public static Qualification fromCursor(Cursor c) {
        Qualification q = new Qualification();
        q.id = c.getInt(c.getColumnIndex("QualificationId"));
        q.name = c.getString(c.getColumnIndex("QualificationName"));
        q.salary = c.getInt(c.getColumnIndex("QualificationSalary"));
        return q;
    }
}
