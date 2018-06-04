package com.regent.tech.numberisfun;

import android.content.Intent;
import android.support.v4.app.ShareCompat;
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
        switch (id){
            case android.R.id.home:
                Intent homeActivity = new Intent(this, MainActivity.class);
                startActivity(homeActivity);
                finish();
                return true;
            case R.id.action_share:
                shareText();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareText(){
        String textToShare = factTextView.getText().toString().trim();
        String mimeType = "text/plain";
        String title = "Number Fact";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle(title)
                .setText(textToShare)
                .startChooser();
    }

    @Override
    public void onBackPressed(){
        Intent homeActivity = new Intent(this, MainActivity.class);
        startActivity(homeActivity);
        finish();
    }

}


