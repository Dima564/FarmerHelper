package com.kpi.kovalenkodima.farmerhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.kpi.kovalenkodima.farmerhelper.model.Field;
import com.kpi.kovalenkodima.farmerhelper.model.Plant;
import com.kpi.kovalenkodima.farmerhelper.model.Qualification;

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
                    INSTANCE = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
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
        ContentValues cv = new ContentValues(2);
        cv.put("TechnologyName", plant.technologicalMap.name);
        cv.put("TechnologyMonth", plant.technologicalMap.month);
        cv.put("TechnologyProcessingTime", plant.technologicalMap.processingTime);
        cv.put("TechnologyFuelNeeded", plant.technologicalMap.fuelNeeded);
        long technologicalMapId = getWritableDatabase().insert("TechnologyMap", null, cv);
        cv.clear();
        cv.put("PlantName", plant.name);
        cv.put("Plant_fk_Technology", technologicalMapId);

        getWritableDatabase().insert("Plant", null, cv);
    }

    public List<Plant> getAllPlants() {
        List<Plant> fields = new ArrayList<>();
        Cursor c = getReadableDatabase().rawQuery(
                "SELECT * FROM Plant JOIN TechnologyMap on Plant.Plant_fk_Technology=TechnologyMap.TechnologyId", null);
        if (c.moveToFirst()) {
            do {
                fields.add(Plant.fromCursor(c));
            } while (c.moveToNext());
        }
        return fields;

    }

    public Plant getPlantById(Integer id) {
        String[] selectionArgs = {"" + id};
        Cursor c = getReadableDatabase()
                .rawQuery("SELECT * FROM Plant JOIN TechnologyMap on Plant.Plant_fk_Technology=TechnologyMap.TechnologyId where PlantId=?", selectionArgs);
        if (c.moveToFirst()) {
            return Plant.fromCursor(c);
        } else {
            return null;
        }
    }

    public void insertField(Field field) {
        ContentValues cv = new ContentValues(3);
        cv.put("FieldName", field.name);
        cv.put("FieldAddress", field.address);
        cv.put("Field_fk_Plant", field.plantId);

        getWritableDatabase().insert("Field", null, cv);
    }

    public void plantCropsOnField(Integer plantId, Integer fieldId) {
        String [] whereArgs = {"" + fieldId};
        ContentValues cv = new ContentValues();
        cv.put("Field_fk_Plant",plantId);
        getWritableDatabase().update("Field",cv,"FieldId=?",whereArgs);
    }

    public void emptyField(Integer fieldId) {
        plantCropsOnField(-1,fieldId);
    }

    public List<Field> getAllFields() {
        List<Field> fields = new ArrayList<>();
        Cursor c = getReadableDatabase().query("Field", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                fields.add(Field.fromCursor(c));
            } while (c.moveToNext());
        }
        return fields;
    }

    public List<Field> getFieldsWithPlant(Integer plantId) {
        String [] whereArgs = {"" + plantId};
        List<Field> fields = new ArrayList<>();
        Cursor c = getReadableDatabase().query("Field",null,"Field_fk_Plant=?",whereArgs,null,null,null);
        if (c.moveToFirst()) {
            do {
                fields.add(Field.fromCursor(c));
            } while (c.moveToNext());
        }
        return fields;
    }

    public void insertQualification(Qualification q) {
        ContentValues cv = new ContentValues();
        cv.put("QualificationName",q.name);
        cv.put("QualificationSalary",q.salary);
        getWritableDatabase().insert("Qualification",null,cv);
    }

    public void insertQualificationRequirement(Integer technologyId, Integer qualificationId) {
        ContentValues cv = new ContentValues(2);
        cv.put("fk_TechnologyMap", technologyId);
        cv.put("fk_Qualification", qualificationId);
        getWritableDatabase().insert("RequiresQualification",null,cv);
    }

    public List<Qualification> getQualificationsForTechnology(Integer technologyId) {
        String [] selectionArgs = {"" + technologyId};
        Cursor c = getReadableDatabase().rawQuery("SELECT * FROM Qualification JOIN RequiresQualification ON RequiresQualification.fk_Qualification=Qualification.QualificationId WHERE RequiresQualification.fk_TechnologyMap=?",selectionArgs);

        List<Qualification> qs = new ArrayList<>();
        if (c.moveToFirst()) {
            do {
                qs.add(Qualification.fromCursor(c));
            } while (c.moveToNext());
        }
        return qs;
    }

    public void removeQualificationRequirement(Integer technologyId, Integer qualificationId) {
        String [] whereArgs = {"" +technologyId, "" + qualificationId};
        getWritableDatabase().delete("RequiresQualification","fk_TechnologyMap=? AND fk_Qualification=?",whereArgs);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public List<Qualification> getAllQualifications() {
        List<Qualification> qs = new ArrayList<>();
        Cursor c = getReadableDatabase().query("Qualification", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                qs.add(Qualification.fromCursor(c));
            } while (c.moveToNext());
        }
        return qs;
    }
}
