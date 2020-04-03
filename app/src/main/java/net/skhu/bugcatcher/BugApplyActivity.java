package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class BugApplyActivity extends AppCompatActivity {
    private EditText inputContent;
    private EditText inputReward;
    private RadioGroup sizeGroup;
    private RadioButton big, middle, small;
    String errorMessage = "";
    private String content;
    private String reward;
    private String email;
    private String size;

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_apply);


        inputContent = findViewById(R.id.apply_content);
        inputReward = findViewById(R.id.apply_reward);
        sizeGroup = (RadioGroup) findViewById(R.id.size_group);

        big = (RadioButton) findViewById(R.id.bigBtn);
        middle = (RadioButton) findViewById(R.id.middleBtn);
        small = (RadioButton) findViewById(R.id.smallBtn);

    }

    public void apply(View v) {
        content = inputContent.getText().toString();
        reward = inputReward.getText().toString();
        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        email=sharedpreferences.getString("email",null);
        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        switch (sizeGroup.getCheckedRadioButtonId()) {
            case R.id.bigBtn: size="대"; break;
            case R.id.middleBtn:size="중"; break;
            case R.id.smallBtn:size="소"; break;
        }
        if (isValidInfo()) {
            saveApply(email,content,reward,size);
        }
        else{
            Toast.makeText(BugApplyActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isValidInfo() {

        if (content.isEmpty()) {
            errorMessage = "신고 내용을 입력하세요.";
        }
        else if (reward.isEmpty()) {
            errorMessage = "보상을 입력하세요.";
        }
        else if (size.isEmpty()) {
            errorMessage = "벌레 크기를 선택하세요.";
        }
        else{
            return true;
        }
        return false;
    }


    //신고내용 저장
    private void saveApply(String email,String content, String reward, String size) {
        Apply apply= new Apply(email,content,reward,size);
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String current=sdf.format(c.getTime());
        ref.child("apply").child(current).setValue(apply);
        Toast.makeText(BugApplyActivity.this, "수배 성공", Toast.LENGTH_SHORT).show();
    }

}
