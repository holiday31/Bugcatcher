package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class BugApplyActivity extends AppCompatActivity {
    private EditText inputContent;
    private EditText inputReward;
    private RadioGroup sizeGroup;
    private RadioButton big, middle, small;
    private Spinner spinner;
    String errorMessage = "";
    private String content;
    private String reward;
    private String email;
    private String size;
    private int rewardIndex;
    private int maxreward;
    private double latitude=37.5026930;
    private double longitude=126.4257;
    private String address1="경기도 부천시";
    private String address2="1";
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bug_apply);

        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        email=sharedpreferences.getString("email",null);

        intent=new Intent(BugApplyActivity.this, CatcherListActivity.class);
        ref.child("apply").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                    String applyId = snapshot1.getKey();
                    if(snapshot1.child("email").getValue(String.class).equals(email)){
                        Log.d("check","email same");
                        if(snapshot1.child("state").getValue(Integer.class)==0){
                            Log.d("check","state 0");
                            intent.putExtra("applyId",applyId);
                            startActivity(intent);
                        }

                    }
                }
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
        inputContent = findViewById(R.id.apply_content);
        inputReward = findViewById(R.id.apply_reward);
        sizeGroup = (RadioGroup) findViewById(R.id.size_group);

        big = (RadioButton) findViewById(R.id.bigBtn);
        middle = (RadioButton) findViewById(R.id.middleBtn);
        small = (RadioButton) findViewById(R.id.smallBtn);
        spinner = (Spinner) findViewById(R.id.spinner);
        rewardIndex = spinner.getSelectedItemPosition();

    }

    public void apply(View v) {
        content = inputContent.getText().toString();
        reward = inputReward.getText().toString();

        //Toast.makeText(this, email, Toast.LENGTH_SHORT).show();
        switch (sizeGroup.getCheckedRadioButtonId()) {
            case R.id.bigBtn: size="대"; break;
            case R.id.middleBtn:size="중"; break;
            case R.id.smallBtn:size="소"; break;
        }
        switch (rewardIndex) {
            case 0: maxreward=5000; break;
            case 1: maxreward=10000; break;
            case 2: maxreward=15000; break;
            case 3: maxreward=20000; break;
        }
        if (isValidInfo()) {
            saveApply(email,content,size,reward,maxreward,latitude,longitude,address1,address2);
        }
        else{
            Toast.makeText(BugApplyActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private void check(){

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

    private void saveApply(String email,String content, String size, String reward,int maxreward,double latitude,double longitude,String address1,String address2) {
        Apply apply= new Apply(email,content,size,reward,maxreward,latitude,longitude,address1,address2,0);
        Calendar c=Calendar.getInstance();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddhhmmss");
        String current=sdf.format(c.getTime());
        ref.child("apply").child(current).setValue(apply);
        Toast.makeText(BugApplyActivity.this, "수배 성공", Toast.LENGTH_SHORT).show();
        Intent applyIntent= new Intent(BugApplyActivity.this,CatcherListActivity.class);
        applyIntent.putExtra("applyId",current);
        startActivity(applyIntent);
    }

}
