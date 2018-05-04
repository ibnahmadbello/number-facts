package com.regent.tech.numberisfun.Utilities;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;

public class NumberQueryTask extends AsyncTask<URL, Void, String> {

    @Override
    protected void onPreExecute(){super.onPreExecute();}

    @Override
    protected String doInBackground(URL... urls) {
        URL searchUrl = urls[0];
        String numberQueryResult = null;
        try {
            numberQueryResult = NetworkUtils.getResponseFromHttpUrl(searchUrl);
        } catch (IOException e){
            e.printStackTrace();
        }
        return numberQueryResult;
    }

    @Override
    protected void onPostExecute(String numberQueryResult){
        if (numberQueryResult != null && !numberQueryResult.equals("")){
            //TODO: call the display method here

        }
    }
}
