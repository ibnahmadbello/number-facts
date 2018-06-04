package com.regent.tech.numberisfun;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.regent.tech.numberisfun.Data.NumberContract;
import com.regent.tech.numberisfun.Data.NumberDbHelper;
import com.regent.tech.numberisfun.Utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String>, View.OnClickListener,
        NumberAdapter.NumberAdapterOnClickHandler{

    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    private static final String TAG = MainActivity.class.getSimpleName();

    private NumberAdapter numberAdapter;
    private EditText mSearchBoxEditText;
    private TextView mQueryResult;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private TextView mPreviousSearch;
    RecyclerView numberRecyclerView;
    private SQLiteDatabase sqLiteDatabase;


    private static final int NUMBER_SEARCH_LOADER = 11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mSearchBoxEditText = findViewById(R.id.search_box);

        mQueryResult = findViewById(R.id.search_result);
        mQueryResult.setOnClickListener(this);

        mPreviousSearch = findViewById(R.id.previous_search);

        mErrorMessage = findViewById(R.id.error_message_display);

        mProgressBar = findViewById(R.id.progress_indicator);

        numberRecyclerView = findViewById(R.id.search_history);
        numberRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NumberDbHelper dbHelper = new NumberDbHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        Cursor cursor = getNumberFact();

        numberAdapter = new NumberAdapter(this, cursor, this);

        numberRecyclerView.setAdapter(numberAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                long id = (long) viewHolder.itemView.getTag();

                removeNumber(id);

                numberAdapter.swapCursor(getNumberFact());
            }
        }).attachToRecyclerView(numberRecyclerView);




        getSupportLoaderManager().initLoader(NUMBER_SEARCH_LOADER, null, this);

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
        addNewFact(numberQueryResult);

        // Update the cursor in the adapter to trigger UI to display the new list
        numberAdapter.swapCursor(getNumberFact());

        //clear UI text fields
//        mSearchBoxEditText.clearFocus();
//        mSearchBoxEditText.getText().clear();

    }

    private void makeNumberQuerySearch(){
        String numberQuery = mSearchBoxEditText.getText().toString();
        if (TextUtils.isEmpty(numberQuery)){
            mErrorMessage.setText(R.string.empty_text_view);
            return;
        }

        URL numberSearchUrl = NetworkUtils.buildUrl(numberQuery);

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, numberSearchUrl.toString());

        LoaderManager loaderManager = getSupportLoaderManager();

        Loader<String> numberSearchLoader = loaderManager.getLoader(NUMBER_SEARCH_LOADER);

        if (numberSearchLoader == null){
            loaderManager.initLoader(NUMBER_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(NUMBER_SEARCH_LOADER, queryBundle, this);
        }


    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            @Override
            protected void onStartLoading(){
                if (args == null){
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);
                preloading();

                forceLoad();

            }

            @Nullable
            @Override
            public String loadInBackground() {
                String numberQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                if (numberQueryUrlString == null || TextUtils.isEmpty(numberQueryUrlString)){
                    return null;
                }

                try {
                    URL numberUrl = new URL(numberQueryUrlString);
                    String numberSearchResults = NetworkUtils.getResponseFromHttpUrl(numberUrl);
                    return numberSearchResults;
                } catch (IOException e){
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        mProgressBar.setVisibility(View.INVISIBLE);
        if (null == data){
            mErrorMessage.setVisibility(View.VISIBLE);
            mErrorMessage.setText(R.string.error_message);
        } else {
            showResult(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    /*class NumberQueryTask extends AsyncTask<String, Void, String> {

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

    */

    private void preloading(){
        mProgressBar.setVisibility(View.VISIBLE);
        mQueryResult.setVisibility(View.INVISIBLE);
        mErrorMessage.setVisibility(View.INVISIBLE);
        mSearchBoxEditText.setEnabled(false);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.search_result){
            if (mQueryResult.getText().toString().isEmpty()){
                return;
            }
            Intent shareIntent = new Intent(this, ShareActivity.class);
            String textToShare = mQueryResult.getText().toString();
            shareIntent.putExtra(Intent.EXTRA_TEXT, textToShare);
            startActivity(shareIntent);
        }
    }

    public Cursor getNumberFact(){
        return sqLiteDatabase.query(
                NumberContract.NumberEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );
    }

    private long addNewFact(String numberQueryResult){
        ContentValues values = new ContentValues();
        values.put(NumberContract.NumberEntry.COLUMN_RESULT, numberQueryResult);
        Log.d(TAG, "The saved data is: " + numberQueryResult);
        return sqLiteDatabase.insert(NumberContract.NumberEntry.TABLE_NAME, null, values);
    }

    private boolean removeNumber(long id){
        return sqLiteDatabase.delete(NumberContract.NumberEntry.TABLE_NAME,
                NumberContract.NumberEntry._ID + "=" + id, null) > 0;
    }

    @Override
    public void onClick(String numberData) {
        Intent startShareActivity = new Intent(this, ShareActivity.class);
        startShareActivity.putExtra(Intent.EXTRA_TEXT, numberData);
        startActivity(startShareActivity);
    }
}
