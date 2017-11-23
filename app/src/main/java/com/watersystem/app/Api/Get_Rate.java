package com.watersystem.app.Api;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.watersystem.app.Includes.Rate;
import com.watersystem.app.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;

import static com.watersystem.app.Includes.Config.getUrl;


public class Get_Rate  extends AsyncTask<String, Void, String> {


    private ProgressDialog dialog;

    public Get_Rate(MainActivity activity){ dialog = new ProgressDialog(activity);}

    @Override
    protected String doInBackground(String... urls) {
        return getUrl(urls[0]);
    }

    @Override
    protected void onPreExecute() {
        dialog.setMessage("Synch ...");
        dialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        try {

            JSONArray rows = new JSONArray(result);

            Rate delete_Rate = new Rate();
            delete_Rate.delete_Rate();

            for(int i = 0; i < rows.length(); i++) {

                String id =  rows.getJSONObject(i).getString("id");
                String type = rows.getJSONObject(i).getString("type");
                String minrate = rows.getJSONObject(i).getString("minrate");
                String mincubic = rows.getJSONObject(i).getString("mincubic");
                String percubic = rows.getJSONObject(i).getString("percubic");
                String tax = rows.getJSONObject(i).getString("tax");
                String status = rows.getJSONObject(i).getString("status");
                String duedate =  rows.getJSONObject(i).getString("duedate");

                Rate rate = new Rate(id,type,minrate,mincubic,percubic,tax,status,duedate);
                rate.add_Rate();
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
