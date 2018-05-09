package com.regent.tech.numberisfun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class ShareActivity extends AppCompatActivity {

    TextView factTextView;
    String numberFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        factTextView = findViewById(R.id.shared_number_fact);

        Intent intent = getIntent();
        if (intent != null){
            if (intent.hasExtra(Intent.EXTRA_TEXT)){
                numberFact = intent.getStringExtra(Intent.EXTRA_TEXT);
                factTextView.setText(numberFact);
            }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
