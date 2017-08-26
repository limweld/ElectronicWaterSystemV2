package com.watersystem.app.Api;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.watersystem.app.Includes.Reading;
import com.watersystem.app.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import static com.watersystem.app.Includes.Config.getUrl;


public class Get_Reading extends AsyncTask<String, Void, String> {
    private ProgressDialog dialog;

    public Get_Reading(MainActivity activity){
        dialog = new ProgressDialog(activity);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Synch ...");
        dialog.show();
    }

    @Override
    protected String doInBackground(String... urls) {
        return getUrl(urls[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        try {

            //JSONObject json = new JSONObject(result); // convert String to JSONObject
            //JSONArray articles = json.getJSONArray("reading");

            JSONArray rows = new JSONArray(result);

            for(int i = 0; i<= rows.length(); i++) {

                String id = rows.getJSONObject(i).getString("id");
                String customer_id = rows.getJSONObject(i).getString("customerid");
                String fullname = rows.getJSONObject(i).getString("fullname");
                String billing_period_id = rows.getJSONObject(i).getString("readingseqno");
                String billing_date = rows.getJSONObject(i).getString("billingdate");
                String employeed_id = rows.getJSONObject(i).getString("employeeid");
                String previous_reading = rows.getJSONObject(i).getString("prevreading");
                String previous_balance = rows.getJSONObject(i).getString("prevbalance");
                String current_reading = rows.getJSONObject(i).getString("currentreading");
                String current_balance = rows.getJSONObject(i).getString("currentbalance");
                String location = rows.getJSONObject(i).getString("location");
                String type = rows.getJSONObject(i).getString("type");
                String meter_number = rows.getJSONObject(i).getString("meter");

                String cashtendered = rows.getJSONObject(i).getString("cashtendered");
                String recietno = rows.getJSONObject(i).getString("receiptno");
                String billfrom = rows.getJSONObject(i).getString("billfrom");
                String billto = rows.getJSONObject(i).getString("billto");


                Reading reading = new Reading(
                        id,                     //0
                        customer_id,            //1
                        fullname,               //2
                        billing_period_id,      //3
                        billing_date,           //4
                        employeed_id,           //5
                        previous_reading,       //6
                        previous_balance,       //7
                        current_reading,        //8
                        current_balance,        //9
                        "New",                  //10
                        location,               //11
                        type,                   //12
                        meter_number,           //13
                        cashtendered,           //14
                        recietno,               //15
                        billfrom,               //17
                        billto                  //18
                 );

                reading.add_Reading();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (dialog.isShowing()) {
            dialog.dismiss();
        }

    }

    @Override
    protected void onCancelled() {
    }
}
