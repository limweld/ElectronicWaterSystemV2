package com.watersystem.app.Includes;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {

    Context context;
    public static SQLiteDatabase db;

    private final String DBNAME = "water_system";
    private final int DBVER = 37;

    //READING TABLE
    private final String READING_TABLE = "reading";
    private final String READING_ID = "id";                                 //0
    private final String READING_CUSTOMERS_ID = "customer_id";              //1
    private final String READING_CUSTOMERS_FULLNAME = "fullname";           //2
    private final String READING_BILLING_PERIOD_ID = "billing_period_id";   //3
    private final String READING_BILLING_DATE = "billing_date";             //4
    private final String READING_EMPLOYEE_ID = "employee_id";               //5
    private final String READING_PREVIOUS_BALANCE = "previous_balance";     //6
    private final String READING_PREVIOUS_METER = "previous_reading";       //7
    private final String READING_CURRENT_BALANCE = "current_balance";       //8
    private final String READING_CURRENT_METER = "current_reading";         //9
    private final String READING_SYNCH_STATUS = "synch_status";             //10
    private final String READING_LOCATION = "location";                     //11
    private final String READING_CONSUMER_TYPE = "type";                    //12
    private final String READING_METER_NUMBER = "meter_number";             //13
    private final String READING_CASHTENDERED =  "cashtendered";            //14
    private final String READING_RECIET_NO = "recietno";                    //15
    private final String READING_BILL_FROM = "billfrom";                    //17
    private final String READING_BILL_TO = "billto";                        //18


    //USER TABLE
    private final String USER_TABLE = "user";
    private final String USER_ID = "employee_id";
    private final String USER_PASSWORD = "user_password";
    private final String USER_FIRSTNAME = "firstname";
    private final String USER_LASTNAME = "lastname";
    private final String USER_LOGIN_STATUS = "status";


    //POLICY TABLE
    private final String POLICY_TABLE = "policy";
    private final String POLICY_ID = "id";
    private final String POLICY_DESCRIPTION = "description";
    private final String POLICY_AMOUNT = "amount";

    //API TABLE
    private final String API_TABLE = "api";
    private final String API_ID = "id";
    private final String API_IP = "ip";

    //RATE TABLE
    private final String RATE_TABLE = "rate";
    //private final String RATE_ID = "id";
    //private final String RATE_TYPE = "type";
    //private final String RATE_MIN_RATE = "min_rate";
    //private final String RATE_PER_CUBIC = "per_cubic";
    //private final String RATE_STATUS = "status";

    private final String RATE_ID = "id";
    private final String RATE_TYPE = "type";
    private final String RATE_MIN_RATE = "minrate";
    private final String RATE_MIN_CUBIC = "mincubic";
    private final String RATE_PER_CUBIC = "percubic";
    private final String RATE_TAX = "tax";
    private final String RATE_STATUS = "status";
    private final String RATE_DUE_DATE = "duedate";

    public DataHandler(){

    }

    public DataHandler(Context context)
    {
        this.context = context;
        CustomHelper helper = new CustomHelper(context);
        this.db = helper.getWritableDatabase();

    }

    private class CustomHelper extends SQLiteOpenHelper {

        public CustomHelper(Context context) {
            super(context, DBNAME, null, DBVER);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {

                String customer_table = "create table " + READING_TABLE +
                        " ("
                        + READING_ID + " text primary key not null,"            //0
                        + READING_CUSTOMERS_ID + " TEXT,"                       //1
                        + READING_CUSTOMERS_FULLNAME + " TEXT,"                 //2
                        + READING_BILLING_PERIOD_ID + " TEXT,"                  //3
                        + READING_BILLING_DATE + " TEXT,"                       //4
                        + READING_EMPLOYEE_ID + " TEXT,"                        //5
                        + READING_PREVIOUS_METER + " TEXT,"                     //6
                        + READING_PREVIOUS_BALANCE + " TEXT,"                   //7
                        + READING_CURRENT_METER + " TEXT,"                      //8
                        + READING_CURRENT_BALANCE + " TEXT,"                    //9
                        + READING_SYNCH_STATUS + " TEXT,"                       //10
                        + READING_LOCATION + " TEXT,"                           //11
                        + READING_CONSUMER_TYPE + " TEXT,"                      //12
                        + READING_METER_NUMBER + " TEXT,"                       //13
                        + READING_CASHTENDERED + " TEXT,"                       //14
                        + READING_RECIET_NO + " TEXT,"                          //15
                        + READING_BILL_FROM + " TEXT,"                          //16
                        + READING_BILL_TO + " TEXT "                            //17
                        + ");";
                db.execSQL(customer_table);

                String user_table = "create table " + USER_TABLE +
                        " ("
                        + USER_ID + "  text primary key not null,"
                        + USER_PASSWORD + " TEXT,"
                        + USER_FIRSTNAME + " TEXT,"
                        + USER_LASTNAME + " TEXT,"
                        + USER_LOGIN_STATUS + " TEXT "
                        + ");";
                db.execSQL(user_table);

                String policy_table = "create table " + POLICY_TABLE +
                        " ("
                        + POLICY_ID + " text primary key not null,"
                        + POLICY_DESCRIPTION + " TEXT,"
                        + POLICY_AMOUNT + " TEXT "
                        + ");";
                db.execSQL(policy_table);

                String api_table = "create table " + API_TABLE +
                        " ("
                        + API_ID + " text primary key not null,"
                        + API_IP + " TEXT "
                        + ");";
                db.execSQL(api_table);

                //String rate_table = "create table " + RATE_TABLE +
                //      " ("
                //      + RATE_ID +  " text primary key not null,"
                //      + RATE_TYPE + " TEXT,"
                //        + RATE_MIN_RATE + " TEXT,"
                //        + RATE_PER_CUBIC + " TEXT,"
                //        + RATE_STATUS + " TEXT "
                //        + ");";
                //db.execSQL(rate_table);

                String rate_table = "create table " + RATE_TABLE +
                " ("
                + RATE_ID +  " text primary key not null,"
                + RATE_TYPE + " TEXT,"
                + RATE_MIN_RATE + " TEXT,"
                + RATE_MIN_CUBIC + " TEXT,"
                + RATE_PER_CUBIC + " TEXT,"
                + RATE_TAX + " TEXT,"
                + RATE_STATUS + " TEXT,"
                + RATE_DUE_DATE + " TEXT "
                + ");";
                db.execSQL(rate_table);


                String api_insert_default_table = "INSERT INTO api (id,ip) VALUES ('1' , '192.168.254.200:8080')";
                db.execSQL(api_insert_default_table);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {

                db.execSQL("DROP TABLE IF EXISTS " + READING_TABLE);

                db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);

                db.execSQL("DROP TABLE IF EXISTS " + POLICY_TABLE);

                db.execSQL("DROP TABLE IF EXISTS " + API_TABLE);

                db.execSQL("DROP TABLE IF EXISTS " + RATE_TABLE);

                onCreate(db);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
