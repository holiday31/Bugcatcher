package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {

    // 비밀번호 정규식
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    private EditText inputEmail;
    private EditText inputPw;
    private EditText passwordCheck;
    private EditText inputPhone;
    private EditText inputName;
    private RadioGroup radioGroup1;

    private String email = "";
    private String password = "";
    private String pwcheck = "";
    private String name = "";
    private String phone = "";
    private String sex="";

    private boolean idCheck=false;
    private boolean phoneCheck=false;

    String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        inputEmail = findViewById(R.id.inputEmail);
        inputPw = findViewById(R.id.inputPw);
        passwordCheck = findViewById(R.id.inputPw2);
        inputPhone = findViewById(R.id.inputPhone);
        inputName = findViewById(R.id.inputName);
        radioGroup1= findViewById(R.id.radioGroup1);



        Button emailCheckbtn=findViewById(R.id.emailCheck);
        emailCheckbtn.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ref.child("users").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                boolean hasEmail=false;
                                email = inputEmail.getText().toString();
                                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                        Log.d("snapshot:",snapshot.child("email").getValue(String.class));

                                        if(snapshot.child("email").getValue(String.class).equals(email))
                                        {
                                            hasEmail= true;
                                            break;
                                        }
                                    }
                                if(hasEmail)
                                    Toast.makeText(SignupActivity.this, R.string.id_check_fail, Toast.LENGTH_SHORT).show();
                                else{
                                    idCheck=true;
                                    Toast.makeText(SignupActivity.this, R.string.id_check_success, Toast.LENGTH_SHORT).show();
                                    inputEmail.setClickable(false);
                                    inputEmail.setFocusable(false);
                                }

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });



                    }
                }) ;
        Button phoneCheckbtn=findViewById(R.id.phoneCheck);
        phoneCheckbtn.setOnClickListener(
                new Button.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ref.child("users").addValueEventListener(new ValueEventListener() {
                            //전화번호 중복확인 완료 후 전화번호 바꿀 시 중복확인 통과 오류 고치기
                            //새로 창을 띄워서 그안에서 아이디 중복확인을 해야할 것 같다.
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                boolean hasPhone=false;
                                phone = inputPhone.getText().toString();
                                Iterator<DataSnapshot> child = dataSnapshot.getChildren().iterator();
                                while(child.hasNext())
                                {
                                    if(child.next().getKey().equals(phone))
                                    {
                                        hasPhone= true;
                                        break;
                                    }
                                }
                                if(hasPhone)
                                    Toast.makeText(SignupActivity.this, R.string.phone_check_fail, Toast.LENGTH_SHORT).show();
                                else{
                                    phoneCheck=true;
                                    Toast.makeText(SignupActivity.this, R.string.phone_check_success, Toast.LENGTH_SHORT).show();
                                    inputPhone.setClickable(false);
                                    inputPhone.setFocusable(false);
                                }

                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }) ;

    }



    public void singUp(View view) {

        email = inputEmail.getText().toString();
        password = inputPw.getText().toString();
        name = inputName.getText().toString();
        phone = inputPhone.getText().toString();
        pwcheck = passwordCheck.getText().toString();
        switch (radioGroup1.getCheckedRadioButtonId()) {
            case R.id.radio0: sex="여자"; break;
            case R.id.radio1: sex="남자"; break;
        }


        if(isValidInfo()) {
            createUser(email, password);
            writeNewUser(email,name,phone,sex);
            //Toast.makeText(SignupActivity.this, "사용자 등록 성공", Toast.LENGTH_SHORT).show();
            Intent intent=new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(SignupActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
        }

    }

    public void setIdCheck(View view) {


    }

    public void setPhoneCheck(View view) {

    }



      // 정보 유효성 검사
      private boolean isValidInfo() {

           if(name.isEmpty()){
              errorMessage="이름을 입력하세요.";
          }
          // 이메일 공백, 이메일 형식 불일치
          else if ((email.isEmpty())||(!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
              errorMessage="이메일을 올바르게 입력하세요.";
          }
          else if(password.isEmpty()||(!PASSWORD_PATTERN.matcher(password).matches())||(password.length()<6)){
              errorMessage="패스워드를 올바르게 입력하세요.";
          }
          else if(pwcheck.isEmpty()||password.equals(passwordCheck)){
              errorMessage="패스워드가 일치하지 않습니다.";
          }
          else if(phone.isEmpty()||(!Patterns.PHONE.matcher(phone).matches())){
               errorMessage="휴대폰번호를 올바르게 입력하세요.";
           }
           else if(sex.isEmpty()){
               errorMessage="성별을 선택하세요.";
           }
           else if(!idCheck){
               errorMessage="아이디 중복확인을 하세요";
           }
           else if(!phoneCheck){
               errorMessage="휴대폰 번호 중복확인을 하세요";
           }
          else{
              return true;
          }
          return false;
      }




      // 회원가입
      private void createUser(String email, String password) {
          firebaseAuth.createUserWithEmailAndPassword(email, password)
                  .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              // 회원가입 성공
                              Toast.makeText(SignupActivity.this, R.string.success_signup, Toast.LENGTH_SHORT).show();

                          } else {
                              // 회원가입 실패
                              Toast.makeText(SignupActivity.this, R.string.failed_signup, Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
      }


      //개인정보저장
      private void writeNewUser(String email,String name, String phone, String sex) {
          UserInfo user = new UserInfo(email,name,phone,sex);
          ref.child("users").child(phone).setValue(user);
      }





}
