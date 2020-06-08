package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        View.OnClickListener loginListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
        View.OnClickListener signupListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        };
        Button loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(loginListener);
        Button signupBtn = (Button) findViewById(R.id.signupBtn);
        signupBtn.setOnClickListener(signupListener);
    }
}
