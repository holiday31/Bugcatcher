package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.view.View;
import android.widget.Toast;

public class BugApplyActivity extends AppCompatActivity {
    private EditText inputContent;
    private EditText inputReward;
    private RadioGroup sizeGroup;
    private RadioButton big, middle, small;
    String errorMessage = "";
    private String content;
    private String reward;
    private String eamil;
    private String size;


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

        if (isValidInfo()) {

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

        else{
            return true;
        }
        return false;
    }


    //신고내용 저장
   /* private void saveApply(String email,String content, String reward, String size) {
        UserInfo user = new UserInfo(email,name,phone,sex);
        Apply
        ref.child("users").child(phone).setValue(user);
    }*/
    //깃 푸쉬중..
}
