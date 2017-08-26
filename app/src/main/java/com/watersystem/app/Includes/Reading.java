package com.watersystem.app.Includes;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static com.watersystem.app.Includes.DataHandler.db;

public class Reading {

    //READING TABLE
    private final String READING_TABLE = "reading";
    private final String READING_ID = "id";
    private final String READING_CUSTOMERS_ID = "customer_id";
    private final String READING_CUSTOMERS_FULLNAME = "fullname";
    private final String READING_BILLING_PERIOD_ID = "billing_period_id";
    private final String READING_EMPLOYEE_ID = "employee_id";    private final String READING_BILLING_DATE = "billing_date";

    private final String READING_PREVIOUS_BALANCE = "previous_balance";
    private final String READING_PREVIOUS_METER = "previous_reading";
    private final String READING_CURRENT_BALANCE = "current_balance";
    private final String READING_CURRENT_METER = "current_reading";
    private final String READING_SYNCH_STATUS = "synch_status";
    private final String READING_LOCATION = "location";
    private final String READING_CONSUMER_TYPE = "type";
    private final String READING_METER_NUMBER = "meter_number";
    private final String READING_CASHTENDERED =  "cashtendered";
    private final String READING_RECIET_NO = "recietno";
    private final String READING_BILL_FROM = "billfrom";
    private final String READING_BILL_TO = "billto";


    private String id;
    private String customer_id;
    private String fullname;
    private String billing_period_id;
    private String billing_date;
    private String previous_reading;
    private String previous_balance;
    private String current_reading;
    private String current_balance;
    private String employeed_id;
    private String synch_status;
    private String location;
    private String type;
    private String meter_number;
    private String cashtendered;
    private String recietno;
    private String status;
    private String billfrom;
    private String billto;


    public String getMeter_number() {
        return meter_number;
    }

    public void setMeter_number(String meter_number) {
        this.meter_number = meter_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSynch_status() {
        return synch_status;
    }

    public void setSynch_status(String synch_status) {
        this.synch_status = synch_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getBilling_period_id() {
        return billing_period_id;
    }

    public void setBilling_period_id(String billing_period_id) {
        this.billing_period_id = billing_period_id;
    }

    public String getBilling_date() {
        return billing_date;
    }

    public void setBilling_date(String billing_date) {
        this.billing_date = billing_date;
    }

    public String getPrevious_reading() {
        return previous_reading;
    }

    public void setPrevious_reading(String previous_reading) {
        this.previous_reading = previous_reading;
    }

    public String getCurrent_reading() {
        return current_reading;
    }

    public void setCurrent_reading(String current_reading) {
        this.current_reading = current_reading;
    }

    public String getPrevious_balance() {
        return previous_balance;
    }

    public void setPrevious_balance(String previous_balance) {
        this.previous_balance = previous_balance;
    }

    public String getCurrent_balance() {
        return current_balance;
    }

    public void setCurrent_balance(String current_balance) {
        this.current_balance = current_balance;
    }

    public String getEmployeed_id() {
        return employeed_id;
    }

    public void setEmployeed_id(String employeed_id) {
        this.employeed_id = employeed_id;
    }

    public String getCashtendered() {
        return cashtendered;
    }

    public void setCashtendered(String cashtendered) {
        this.cashtendered = cashtendered;
    }

    public String getRecietno() {
        return recietno;
    }

    public void setRecietno(String recietno) {
        this.recietno = recietno;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBillfrom() {
        return billfrom;
    }

    public void setBillfrom(String billfrom) {
        this.billfrom = billfrom;
    }

    public String getBillto() {
        return billto;
    }

    public void setBillto(String billto) {
        this.billto = billto;
    }

    public Reading() {
    }


    public Reading(String synch_status) {
        this.synch_status = synch_status;
    }


    public Reading(String id, String synch_status) {
        //this.customer_id = customer_id;
        this.id = id;
        this.synch_status = synch_status;
    }

    public Reading(
            String id,
            String current_reading,
            String current_balance,
            String synch_status,
            String cashtendered,
            String recietno,
            String billing_date,
            String billto
    ) {
        this.id = id;
        this.current_reading = current_reading;
        this.current_balance = current_balance;
        this.synch_status = synch_status;
        this.cashtendered = cashtendered;
        this.recietno = recietno;
        this.billing_date = billing_date;
        this.billto = billto;
    }

    public Reading(
            String id,                  //0
            String customer_id,         //1
            String fullname,            //2
            String billing_period_id,   //3
            String billing_date,        //4
            String employeed_id,        //5
            String previous_reading,    //6
            String previous_balance,    //7
            String current_reading,     //8
            String current_balance,     //9
            String synch_status,        //10
            String location,            //11
            String type) {              //12

        this.id = id;
        this.customer_id = customer_id;
        this.fullname = fullname;
        this.billing_period_id = billing_period_id;
        this.billing_date = billing_date;
        this.employeed_id = employeed_id;
        this.previous_reading = previous_reading;
        this.previous_balance = previous_balance;
        this.current_balance = current_balance;
        this.current_reading = current_reading;
        this.synch_status = synch_status;
        this.location = location;
        this.type = type;
    }

    //READING_CASHTENDERED,       //13
    //READING_RECIET_NO,          //14
    //READING_BILL_TO             //15

    public Reading(
            String id,                  //0
            String customer_id,         //1
            String fullname,            //2
            String billing_period_id,   //3
            String billing_date,        //4
            String employeed_id,        //5
            String previous_reading,    //6
            String previous_balance,    //7
            String current_reading,     //8
            String current_balance,     //9
            String synch_status,        //10
            String location,            //11
            String type,                //12
            String cashtendered,        //13
            String recietno,            //14
            String billto               //15
    ) {
        this.id = id;
        this.customer_id = customer_id;
        this.fullname = fullname;
        this.billing_period_id = billing_period_id;
        this.billing_date = billing_date;
        this.employeed_id = employeed_id;
        this.previous_reading = previous_reading;
        this.previous_balance = previous_balance;
        this.current_balance = current_balance;
        this.current_reading = current_reading;
        this.synch_status = synch_status;
        this.location = location;
        this.type = type;
        this.cashtendered = cashtendered;
        this.recietno = recietno;
        this.billto = billto;
    }

    public Reading(
            String id,                  //0
            String customer_id,         //1
            String fullname,            //2
            String billing_period_id,   //3
            String billing_date,        //4
            String employeed_id,        //5
            String previous_reading,    //6
            String previous_balance,    //7
            String current_reading,     //8
            String current_balance,     //9
            String synch_status,        //10
            String location,            //11
            String type,                //12
            String meter_number         //13

    ) {
        this.id = id;
        this.customer_id = customer_id;
        this.fullname = fullname;
        this.billing_period_id = billing_period_id;
        this.billing_date = billing_date;
        this.employeed_id = employeed_id;
        this.previous_reading = previous_reading;
        this.previous_balance = previous_balance;
        this.current_balance = current_balance;
        this.current_reading = current_reading;
        this.synch_status = synch_status;
        this.location = location;
        this.type = type;
        this.meter_number = meter_number;
    }

    public Reading(
            String id,                  //0
            String customer_id,         //1
            String fullname,            //2
            String billing_period_id,   //3
            String billing_date,        //4
            String employeed_id,        //5
            String previous_reading,    //6
            String previous_balance,    //7
            String current_reading,     //8
            String current_balance,     //9
            String synch_status,        //10
            String location,            //11
            String type,                //12
            String meter_number,        //13
            String cashtendered,        //14
            String recietno,            //15
            String billfrom,            //17
            String billto               //18
    ){
        this.id = id;                                   //0
        this.customer_id = customer_id;                 //1
        this.fullname = fullname;                       //2
        this.billing_period_id = billing_period_id;     //3
        this.billing_date = billing_date;               //4
        this.employeed_id = employeed_id;               //5
        this.previous_reading = previous_reading;       //6
        this.previous_balance = previous_balance;       //7
        this.current_balance = current_balance;         //8
        this.current_reading = current_reading;         //9
        this.synch_status = synch_status;               //10
        this.location = location;                       //11
        this.type = type;                               //12
        this.meter_number = meter_number;               //13
        this.cashtendered = cashtendered;               //14
        this.recietno = recietno;                       //15
        this.billfrom = billfrom;                       //17
        this.billto = billto;                           //18
    }



    public void add_Reading()
    {
        ContentValues values = new ContentValues();

        values.put(READING_ID, getId());                                //0
        values.put(READING_CUSTOMERS_ID,getCustomer_id());              //1
        values.put(READING_CUSTOMERS_FULLNAME, getFullname());          //2
        values.put(READING_BILLING_PERIOD_ID,getBilling_period_id());   //3
        values.put(READING_BILLING_DATE,getBilling_date());             //4
        values.put(READING_EMPLOYEE_ID,getEmployeed_id());              //5
        values.put(READING_PREVIOUS_METER,getPrevious_reading());       //6
        values.put(READING_PREVIOUS_BALANCE,getPrevious_balance());     //7
        values.put(READING_CURRENT_METER,getCurrent_reading());         //8
        values.put(READING_CURRENT_BALANCE,getCurrent_balance());       //9
        values.put(READING_SYNCH_STATUS,getSynch_status());             //10
        values.put(READING_LOCATION,getLocation());                     //11
        values.put(READING_CONSUMER_TYPE,getType());                    //12
        values.put(READING_METER_NUMBER,getMeter_number());             //13
        values.put(READING_CASHTENDERED,getCashtendered());             //14
        values.put(READING_RECIET_NO,getRecietno());                    //15
        values.put(READING_BILL_FROM,getBillfrom());                    //16
        values.put(READING_BILL_TO,getBillto());                        //17


        try{
            db.insert(READING_TABLE, null, values);
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void delete_Reading()
    {
        try {
             db.delete(READING_TABLE, READING_SYNCH_STATUS+ "='"+getSynch_status()+"'", null);}
        catch (Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void update_Reading_Status()
    {
        ContentValues values = new ContentValues();
        values.put(READING_SYNCH_STATUS,getSynch_status());

        try{
            db.update(READING_TABLE, values, READING_ID + " =?",
                new String[] { getId() });
        }
        catch(Exception e)
        {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public void update_Reading_Current_Balance() {

        ContentValues values = new ContentValues();

        values.put(READING_CURRENT_METER, getCurrent_reading());
        values.put(READING_CURRENT_BALANCE, getCurrent_balance());
        values.put(READING_SYNCH_STATUS,getSynch_status());
        values.put(READING_CASHTENDERED,getCashtendered());
        values.put(READING_RECIET_NO,getRecietno());
        values.put(READING_BILLING_DATE,getBilling_date());
        values.put(READING_BILL_TO,getBillto());

        try{
            // updating row
            db.update(READING_TABLE, values, READING_ID + " =?",
                    new String[] { getId() });

        } catch(Exception e) {
            Log.e("DB ERROR", e.toString());
            e.printStackTrace();
        }
    }

    public Reading get_Reading_DATA(String id){

        Cursor cursor = db.query(READING_TABLE,
                new String[] {
                    READING_ID,                 //0
                    READING_CUSTOMERS_ID,       //1
                    READING_CUSTOMERS_FULLNAME, //2
                    READING_BILLING_PERIOD_ID,  //3
                    READING_BILLING_DATE,       //4
                    READING_EMPLOYEE_ID,        //5
                    READING_PREVIOUS_METER,     //6
                    READING_PREVIOUS_BALANCE,   //7
                    READING_CURRENT_METER,      //8
                    READING_CURRENT_BALANCE,    //9
                    READING_SYNCH_STATUS,       //10
                    READING_LOCATION,           //11
                    READING_CONSUMER_TYPE,      //12
                    READING_CASHTENDERED,       //13
                    READING_RECIET_NO,          //14
                    READING_BILL_TO             //15

                },
                READING_ID + "=?",
                new String[] { id },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Reading reading = new Reading(
            cursor.getString(0),
            cursor.getString(1),
            cursor.getString(2),
            cursor.getString(3),
            cursor.getString(4),
            cursor.getString(5),
            cursor.getString(6),
            cursor.getString(7),
            cursor.getString(8),
            cursor.getString(9),
            cursor.getString(10),
            cursor.getString(11),
            cursor.getString(12),
            cursor.getString(13),
            cursor.getString(14),
            cursor.getString(15)
        );
        return reading;
    }

    public Reading get_Data_Reading(String id){

        Cursor cursor = db.query(READING_TABLE,
                new String[] {
                        READING_ID,                 //0
                        READING_CUSTOMERS_ID,       //1
                        READING_CUSTOMERS_FULLNAME, //2
                        READING_BILLING_PERIOD_ID,  //3
                        READING_BILLING_DATE,       //4
                        READING_EMPLOYEE_ID,        //5
                        READING_PREVIOUS_METER,     //6
                        READING_PREVIOUS_BALANCE,   //7
                        READING_CURRENT_METER,      //8
                        READING_CURRENT_BALANCE,    //9
                        READING_SYNCH_STATUS,       //10
                        READING_LOCATION,           //11
                        READING_CONSUMER_TYPE,      //12
                        READING_METER_NUMBER,       //13
                        READING_CASHTENDERED,       //14
                        READING_RECIET_NO,          //15
                        READING_BILL_FROM,          //16
                        READING_BILL_TO,            //17

                },
                READING_ID + "=?",
                new String[] { id },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Reading reading = new Reading(
                cursor.getString(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3),
                cursor.getString(4),
                cursor.getString(5),
                cursor.getString(6),
                cursor.getString(7),
                cursor.getString(8),
                cursor.getString(9),
                cursor.getString(10),
                cursor.getString(11),
                cursor.getString(12),
                cursor.getString(13),
                cursor.getString(14),
                cursor.getString(15),
                cursor.getString(16),
                cursor.getString(17)
        );

        return reading;
    }


    public List<String> get_List_Data_Reading_Filter(String filter, String status)
    {
        List<String> dataArrays = new ArrayList();
        Cursor cursor;

        try {
            cursor = db.query(
                    READING_TABLE,
                    new String[]{READING_CUSTOMERS_FULLNAME,READING_ID,READING_CUSTOMERS_ID,READING_LOCATION},
                    READING_SYNCH_STATUS+ "='"+ status +"' AND ("+
                    READING_CUSTOMERS_ID + " Like '%" + filter + "%' OR "+
                    READING_CUSTOMERS_FULLNAME + " Like '%" + filter + "%' OR "+
                    READING_ID + " Like '%" + filter  + "%')",
                    null, null, null, null, null
            );
            cursor.moveToFirst();

            if (!cursor.isAfterLast())
            {
                do
                {
                    dataArrays.add(cursor.getString(1) + " : " + cursor.getString(2) + " " +cursor.getString(0)) ;
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

    public static ArrayList<Reading> get_List_Pending(String status)
    {
        ArrayList<Reading> dataArrays = new ArrayList();
        Cursor cursor;

        try {
            //cursor = DataHandler.db.query(
            //        READING_TABLE,
            //        new String[]{READING_ID,READING_CUSTOMERS_ID,READING_CURRENT_METER,READING_CURRENT_BALANCE},
            //        READING_SYNCH_STATUS+ "='"+ status+ "'",
            //        null, null, null, null, null
            //);

            cursor = db.query(
                    "reading",
                    new String[]{
                        "id",
                        "customer_id",
                        "current_balance",
                        "current_reading",
                        "cashtendered",
                        "recietno",
                        "billto",
                        "billing_date"
                    },
                    "synch_status"+ "='"+ status+ "'",
                    null, null, null, null, null
            );




            cursor.moveToFirst();

            if (!cursor.isAfterLast())
            {
                do
                {
                    Reading rawData = new Reading();
                    rawData.setId(cursor.getString(0));
                    rawData.setCustomer_id(cursor.getString(1));
                    rawData.setCurrent_balance(cursor.getString(2));
                    rawData.setCurrent_reading(cursor.getString(3));
                    rawData.setCashtendered(cursor.getString(4));
                    rawData.setRecietno(cursor.getString(5));
                    rawData.setBillto(cursor.getString(6));
                    rawData.setBilling_date(cursor.getString(7));
                    dataArrays.add(rawData);
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

    @Override
    public String toString() {
        return "Reading{" +
                "id='" + id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", fullname='" + fullname + '\'' +
                ", billing_period_id='" + billing_period_id + '\'' +
                ", billing_date='" + billing_date + '\'' +
                ", previous_reading='" + previous_reading + '\'' +
                ", current_reading='" + current_reading + '\'' +
                ", previous_balance='" + previous_balance + '\'' +
                ", current_balance='" + current_balance + '\'' +
                ", employeed_id='" + employeed_id + '\'' +
                ", synch_status='" + synch_status + '\'' +
                ", location='" + location + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
