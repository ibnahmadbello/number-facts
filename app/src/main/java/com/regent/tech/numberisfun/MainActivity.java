package com.regent.tech.numberisfun;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.regent.tech.numberisfun.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mQueryResult;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private TextView mPreviousSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = findViewById(R.id.search_box);

        mQueryResult = findViewById(R.id.search_result);

        mPreviousSearch = findViewById(R.id.previous_search);

        mErrorMessage = findViewById(R.id.error_message_display);

        mProgressBar = findViewById(R.id.progress_indicator);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        int id = menuItem.getItemId();
        switch (id){
            case R.id.action_delete:
                // TODO: Make the search history to delete
                break;
            case R.id.action_search:
                makeNumberQuerySearch();
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

    public void showResult(String numberQueryResult){
        mProgressBar.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mSearchBoxEditText.setText("");
        mSearchBoxEditText.setEnabled(true);
        mQueryResult.setVisibility(View.VISIBLE);
        mQueryResult.setText(numberQueryResult);
    }

    private void makeNumberQuerySearch(){
        String numberQuery = mSearchBoxEditText.getText().toString();
        new NumberQueryTask().execute(numberQuery);
    }

    class NumberQueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            preloading();
        }

        @Override
        protected String doInBackground(String... params) {
            String searchUrl = params[0];
            String numberQueryResult = null;
            URL numberRequestURl = NetworkUtils.buildUrl(searchUrl);

            try {
                numberQueryResult = NetworkUtils.getResponseFromHttpUrl(numberRequestURl);
            } catch (IOException e){
                e.printStackTrace();
            }
            return numberQueryResult;
        }

        @Override
        protected void onPostExecute(String numberQueryResult){
            if (numberQueryResult != null && !numberQueryResult.equals("")){
                showResult(numberQueryResult);
            } else {
                mProgressBar.setVisibility(View.INVISIBLE);
                mQueryResult.setVisibility(View.INVISIBLE);
                mErrorMessage.setVisibility(View.VISIBLE);
                mErrorMessage.setText(R.string.error_message);
            }
        }
    }

    private void preloading(){
        mProgressBar.setVisibility(View.VISIBLE);
        mQueryResult.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mSearchBoxEditText.setEnabled(false);
    }


}
