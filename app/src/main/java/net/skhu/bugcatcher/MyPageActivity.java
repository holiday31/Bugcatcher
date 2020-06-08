package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
    }

    public void info_clicked(View view) {
        Intent infoIntent = new Intent(this, MyinfoActivity.class);
        startActivity(infoIntent);
    }

    public void progress_clicked(View view) {
        Intent progressIntent = new Intent(this, ProgressActivity.class);
        startActivity(progressIntent);
    }
}
