package com.watersystem.app.Includes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;

import static com.watersystem.app.Includes.DataHandler.db;


public class Rate {

    private final String RATE_TABLE = "rate";
    private final String RATE_ID = "id";
    private final String RATE_TYPE = "type";
    private final String RATE_MIN_RATE = "minrate";
    private final String RATE_MIN_CUBIC = "mincubic";
    private final String RATE_PER_CUBIC = "percubic";
    private final String RATE_TAX = "tax";
    private final String RATE_STATUS = "status";
    private final String RATE_DUE_DATE = "duedate";

    private String id;
    private String type;
    private String minrate;
    private String mincubic;
    private String percubic;
    private String tax;
    private String status;
    private String duedate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMinrate() {
        return minrate;
    }

    public void setMinrate(String minrate) {
        this.minrate = minrate;
    }

    public String getMincubic() {
        return mincubic;
    }

    public void setMincubic(String mincubic) {
        this.mincubic = mincubic;
    }

    public String getPercubic() {
        return percubic;
    }

    public void setPercubic(String percubic) {
        this.percubic = percubic;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDuedate() {
        return duedate;
    }

    public void setDuedate(String duedate) {
        this.duedate = duedate;
    }

    public Rate() {
    }

    public Rate(String id, String type, String minrate, String mincubic, String percubic, String tax, String status, String duedate) {
        this.id = id;
        this.type = type;
        this.minrate = minrate;
        this.mincubic = mincubic;
        this.percubic = percubic;
        this.tax = tax;
        this.status = status;
        this.duedate = duedate;
    }

    public void add_Rate(){

        ContentValues values = new ContentValues();

        values.put(RATE_ID,getId());
        values.put(RATE_TYPE,getType());
        values.put(RATE_MIN_RATE,getMinrate());
        values.put(RATE_MIN_CUBIC,getMincubic());
        values.put(RATE_PER_CUBIC,getPercubic());
        values.put(RATE_TAX,getTax());
        values.put(RATE_STATUS,getStatus());
        values.put(RATE_DUE_DATE,getDuedate());


        try{
            db.insert(RATE_TABLE, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public Rate get_Data_Rate(String id){
        Cursor cursor = db.query(RATE_TABLE,
                new String[] {
                        RATE_ID,
                        RATE_TYPE,
                        RATE_MIN_RATE,
                        RATE_MIN_CUBIC,
                        RATE_PER_CUBIC,
                        RATE_TAX,
                        RATE_STATUS,
                        RATE_DUE_DATE
                },
                RATE_ID + "=?",
                new String[] { id },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Rate rate = new Rate(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7));

        return rate;
    }

    public ArrayList<ArrayList<Object>> get_Rate_Rows()
    {
        ArrayList<ArrayList<Object>> dataArrays = new ArrayList<ArrayList<Object>>();
        Cursor cursor;

        try
        {
            cursor = db.query(
                    RATE_TABLE,
                    new String[] {
                            RATE_ID,
                            RATE_TYPE,
                            RATE_MIN_RATE,
                            RATE_MIN_CUBIC,
                            RATE_PER_CUBIC,
                            RATE_TAX,
                            RATE_STATUS,
                            RATE_DUE_DATE
                    },
                    null, null, null, null, null
            );

            cursor.moveToFirst();

            if (!cursor.isAfterLast())
            {
                do
                {
                    ArrayList<Object> dataList = new ArrayList<>();

                    dataList.add(cursor.getString(0));
                    dataList.add(cursor.getString(1));
                    dataList.add(cursor.getString(2));
                    dataList.add(cursor.getString(3));
                    dataList.add(cursor.getString(4));
                    dataList.add(cursor.getString(5));
                    dataList.add(cursor.getString(6));
                    dataList.add(cursor.getString(7));

                    dataArrays.add(dataList);
                }

                while (cursor.moveToNext());
            }
        }
        catch (SQLException e)
        {
            Log.e("DB Error", e.toString());
            e.printStackTrace();
        }

        return dataArrays;
    }

}
