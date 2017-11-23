package com.watersystem.app.Includes;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {

    private String url_Reading_data;
    private String url_Rate_data;
    private String url_User_data;
    private String url_Update_Reading;
    private String url_Change_Password;

    public void setUrl_Reading_data(String IP) {
        this.url_Reading_data = "http://"+IP+"/mssqlwater/mobile/reading_json.php";
    }

    public void setUrl_Rate_data(String IP) {
        this.url_Rate_data = "http://"+IP+"/mssqlwater/mobile/cubicrate_json.php";
    }

    public void setUrl_User_data(String IP) {
        this.url_User_data = "http://"+IP+"/mssqlwater/mobile/login_json.php";
    }

    public void setUrl_Update_Reading(String IP){
        this.url_Update_Reading = "http://"+IP+"/mssqlwater/mobile/upload.php";
    }

    public void setUrl_Change_Password(String IP){
        this.url_Change_Password = "http://"+IP+"/mssqlwater/mobile/collector_changepass_json.php";
    }

    public String getUrl_Reading_data() {
        return url_Reading_data;
    }

    public String getUrl_Rate_data() {
        return url_Rate_data;
    }

    public String getUrl_User_data() { return url_User_data; }

    public String getUrl_Update_Reading(){ return url_Update_Reading; }

    public String getUrl_Change_Password(){ return url_Change_Password;}

    public Config() {
    }

    public static String getUrl(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }
}
