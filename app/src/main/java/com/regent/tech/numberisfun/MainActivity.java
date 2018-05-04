package com.regent.tech.numberisfun;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mQueryText;
    private TextView mQueryResult;
    private TextView mPreviousSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = findViewById(R.id.search_box);

        mQueryText = findViewById(R.id.search_query);

        mQueryResult = findViewById(R.id.search_result);

        mPreviousSearch = findViewById(R.id.previous_search);

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
                // TODO: Make the search to happen
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

}
