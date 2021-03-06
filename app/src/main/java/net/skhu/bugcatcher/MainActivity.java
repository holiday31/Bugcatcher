package net.skhu.bugcatcher;
import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;

    // 이메일과 비밀번호
    private EditText editTextEmail;
    private EditText editTextPassword;

    private String email = "";
    private String password = "";


    public static final String MyPREFERENCES = "Session" ;
    SharedPreferences sharedpreferences;
    String errorM;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 파이어베이스 인증 객체 선언
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = findViewById(R.id.et_eamil);
        editTextPassword = findViewById(R.id.et_password);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        //SharedPreferences prefs = getPreferences(this);
        value =sharedpreferences.getString("email",null);

        if((value!=null)&&!value.isEmpty()){
            //Toast.makeText(MainActivity.this, email, Toast.LENGTH_SHORT).show();
            Intent applyIntent=new Intent(this, MainMapActivity.class);
            startActivity(applyIntent);
        }



    }

    public void singUp(View view) {
        /*
        email = editTextEmail.getText().toString();
        password = editTextPassword.getText().toString();

        if(isValidEmail() && isValidPasswd()) {
            createUser(email, password);
        }
         */
        Intent signUpIntent = new Intent(this, SignupActivity.class);
        startActivity(signUpIntent);
    }

    public void signIn(View view) {
        email = editTextEmail.getText().toString().trim();
        password = editTextPassword.getText().toString().trim();

        if (isValidEmail() && isValidPasswd()) {
            loginUser(email, password);
        }
        else
            Toast.makeText(MainActivity.this, errorM, Toast.LENGTH_SHORT).show();

    }


    public void findID_clicked(View view) {
        Intent findIdIntent = new Intent(this, FindIdActivity.class);
        startActivity(findIdIntent);
    }

    public void findpassword_clicked(View view) {
        Intent findpasswordIntent = new Intent(this, FindPwActivity.class);
        startActivity(findpasswordIntent);
    }


    // 이메일 유효성 검사
    private boolean isValidEmail() {
        if (email.isEmpty()) {
            // 이메일 공백
            errorM="이메일을 입력해주세요";
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // 이메일 형식 불일치
            errorM="올바른 이메일을 입력해주세요";
            return false;
        } else {
            return true;
        }
    }

    // 비밀번호 유효성 검사
    private boolean isValidPasswd() {
        if (password.isEmpty()) {
            // 비밀번호 공백
            errorM="비밀번호를 입력해주세요";
            return false;
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // 비밀번호 형식 불일치
            errorM="올바른 비밀번호를 입력해주세요";
            return false;
        } else {
            return true;
        }
    }


    // 로그인
    private void loginUser(final String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 로그인 성공
                            Toast.makeText(MainActivity.this, R.string.success_login, Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("email", email);
                            editor.commit();
                            Intent mainMapIntent = new Intent(getApplicationContext(), MainMapActivity.class);
                            startActivity(mainMapIntent);

                        } else {
                            // 로그인 실패
                            Toast.makeText(MainActivity.this, R.string.failed_login, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}