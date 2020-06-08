package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CallerListDetailActivity extends Activity {
    Apply apply;
    String applyId;
    int distanceValue;
    TextView distance;
    TextView size;
    TextView content;
    TextView maxReward;
    TextView reward;
    ListView listview;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    String phone="01011111111";
    //int index;
    HashMap<String,String> catcherlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_caller_list_detail);

        Intent intent = getIntent();
        apply = (Apply) intent.getSerializableExtra("apply");
        applyId= intent.getStringExtra("applyId");
        distanceValue=intent.getIntExtra("distance",0);

        distance = (TextView) findViewById(R.id.detail_distance);
        size = (TextView) findViewById(R.id.detail_size);
        content = (TextView) findViewById(R.id.detail_content);
        maxReward = (TextView) findViewById(R.id.detail_maxReward);
        reward = (TextView) findViewById(R.id.detail_reward);

        distance.setText("내 위치로부터 "+distanceValue + "m");
        size.setText(apply.getSize());
        content.setText(apply.getContent());
        maxReward.setText(apply.getMaxreward()+"");
        reward.setText(apply.getReward());
    }


    //호출 버튼 클릭
    public void callCatcher(View v){
        //Intent intent = new Intent(getApplicationContext(), ListDetailActivity.class);
        //intent.putExtra("catcher", catcher);

        ref.child("apply").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(applyId).child("state").getValue(Integer.class)==0){
                    //index=(int)dataSnapshot.child(applyId).child("catcher").getChildrenCount();

                    GenericTypeIndicator<HashMap<String,String>>t = new GenericTypeIndicator<HashMap<String,String>>() {};
                    catcherlist=dataSnapshot.child(applyId).child("catcher").getValue(t);

                    if(!(catcherlist==null)) {
                            if (catcherlist.containsValue(phone))
                                Toast.makeText(CallerListDetailActivity.this, "이미 호출요청한 사용자입니다.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        ref.child("apply").child(applyId).child("catcher").push().setValue(phone);
                        ref.child("catcherlist").child(phone).child("apply").push().setValue(applyId);
                        Toast.makeText(CallerListDetailActivity.this, "호출중입니다.", Toast.LENGTH_SHORT).show();
                    }


                }
                else
                    Toast.makeText(CallerListDetailActivity.this, "이미 매칭된 사용자입니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });



        //액티비티(팝업) 닫기
        finish();

    }

    //닫기 버튼 클릭
    public void detailClose(View v){
        //액티비티(팝업) 닫기
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //바깥레이어 클릭시 안닫히게
        if(event.getAction()==MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }


    @Override
    public void onBackPressed() {
        //안드로이드 백버튼 막기
        return;
    }

}
