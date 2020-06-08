package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RatingBar;

public class ReviewActivity extends Activity {

    EditText review_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_review);

        review_txt = (EditText) findViewById(R.id.review_txt);
        RatingBar ratingBar = (RatingBar)findViewById(R.id.ratingBar);

        Intent intent = getIntent();
        String data = intent.getStringExtra("data");
        review_txt.setText(data);


    }

    public void cancel_clicked(View v) {
        //Intent intent = new Intent();
        //intent.putExtra("result", "Close Popup");
        //setResult(RESULT_OK, intent);

        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
    }
