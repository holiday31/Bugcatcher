package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FindPwActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText email;
    private Button findBtn;

    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pw);

        email = (EditText) findViewById(R.id.email);
        findBtn = (Button) findViewById(R.id.findBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        findBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == findBtn) {
            String emailadress = email.getText().toString().trim();
            firebaseAuth.sendPasswordResetEmail(emailadress)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(FindPwActivity.this, "이메일을 보냈습니다.", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            } else {
                                Toast.makeText(FindPwActivity.this, "메일 보내기 실패!", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
        }
    }

}
