package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FindIdActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    private EditText name;
    private EditText phone;

    private String UserName = "";
    private String UserphoneNum = "";
    String errorMessage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_id);

        firebaseAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.name);
        phone = findViewById(R.id.phone);


    }


        public void btn_findId(View view) {
            UserName = name.getText().toString();
            UserphoneNum = phone.getText().toString();

            if(isValidInfo()) {
                Intent intent=new Intent(this, CheckIdActivity.class);
                startActivity(intent);
                findUser(UserName, UserphoneNum);
            }
            else{
                Toast.makeText(FindIdActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }

    }


    private boolean isValidInfo() {

        if(name.getText().toString().length() == 0){
            errorMessage="이름을 입력하세요.";
        }
        else if(phone.getText().toString().length() == 0 ){
            errorMessage="휴대폰번호를 입력하세요.";
        }
        else {
            return true;
        }
        return false;
    }

    private void findUser(String UserName, String UserphoneNum)
    {
        firebaseAuth.signInWithEmailAndPassword(UserName, UserphoneNum)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FindIdActivity.this, "성공 ", Toast.LENGTH_SHORT).show();
                            Intent checkidIntent = new Intent(FindIdActivity.this, CheckIdActivity.class);
                            startActivity(checkidIntent);

                        } else {

                            Toast.makeText(FindIdActivity.this, "실패 ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



}
