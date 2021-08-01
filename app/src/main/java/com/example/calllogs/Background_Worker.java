package com.example.calllogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Background_Worker extends AsyncTask<String,Void,String> {

    String type;
    Context context;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;

    Background_Worker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        type = params[0];


        //REGISTER CODE
            if (type.equals("register")) {
                String reg_url = "http://lms-php.000webhostapp.com/LMS_PHP/register_call_logs.php";
                String num = params[1];
                String  nam = params[2];
                String  dur = params[3];
                String  dt = params[4];
                String  dir = params[5];




                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setDoOutput(true);

                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
                    BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                    String post_data =
                            URLEncoder.encode("num", "UTF-8") + "=" + URLEncoder.encode(num, "UTF-8") +
                                    "&" + URLEncoder.encode("nam", "UTF-8") + "=" + URLEncoder.encode(nam, "UTF-8") +
                                    "&" + URLEncoder.encode("dt", "UTF-8") + "=" + URLEncoder.encode(dt, "UTF-8") +
                                    "&" + URLEncoder.encode("dir", "UTF-8") + "=" + URLEncoder.encode(dir, "UTF-8") +
                                    "&" + URLEncoder.encode("dur", "UTF-8") + "=" + URLEncoder.encode(dur, "UTF-8") ;


                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStreamWriter.close();
                    outputStream.close();


                    InputStream inputStream = httpURLConnection.getInputStream();
                    InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                    String line = "";
                    String result = "";

                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }

                    bufferedReader.close();
                    inputStreamReader.close();
                    inputStream.close();

                    httpURLConnection.disconnect();

                    alertDialog.setTitle("Registration Status");
                    return result;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }


        return null;

    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Alert Message");

    }


    @Override
    protected void onPostExecute(String s) {
        if (s != null) {

            String tt = type;


            if (type.equals("register")) {

                if (s.equals("Failed")) {
                    alertDialog.setMessage("Invalid username or password");
                    alertDialog.show();
                } else {
                    alertDialog.setMessage("Registered");
                    alertDialog.show();

                }
            }


        }

    }
}





