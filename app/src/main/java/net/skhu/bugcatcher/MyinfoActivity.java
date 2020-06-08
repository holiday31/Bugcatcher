package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MyinfoActivity extends AppCompatActivity {

    private static final String TAG = "MyinfoActivity";

    FirebaseAuth firebaseAuth;

    private TextView my_name, my_email, my_phone;
    private EditText my_password, my_password2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        firebaseAuth = FirebaseAuth.getInstance();


        my_name = findViewById(R.id.my_name);
        my_email = findViewById(R.id.my_email);
        my_password = findViewById(R.id.my_password);
        my_password2 = findViewById(R.id.my_password2);
        my_phone = findViewById(R.id.my_phone);



        //로그인 정보 받아오기 (이메일,등 )
        FirebaseUser user = firebaseAuth.getCurrentUser();
        my_email.setText(user.getEmail());
        my_name.setText(user.getDisplayName());
        my_phone.setText(user.getPhoneNumber());


    }

    public void updatePassword() {
        // [START update_password]
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String my_password = "SOME-SECURE-PASSWORD";

        user.updatePassword(my_password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
    }
}