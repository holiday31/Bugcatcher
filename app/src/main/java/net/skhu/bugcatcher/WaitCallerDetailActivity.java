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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WaitCallerDetailActivity extends Activity {
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
    HashMap<String,String> applylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.content_wait_caller_detail);

        Intent intent = getIntent();
        apply = (Apply) intent.getSerializableExtra("apply");
        applyId= intent.getStringExtra("applyId");
        distanceValue=intent.getIntExtra("distance",0);

        distance = (TextView) findViewById(R.id.wait_detail_distance);
        size = (TextView) findViewById(R.id.wait_detail_size);
        content = (TextView) findViewById(R.id.wait_detail_content);
        maxReward = (TextView) findViewById(R.id.wait_detail_maxReward);
        reward = (TextView) findViewById(R.id.wait_detail_reward);

        distance.setText("내 위치로부터 "+distanceValue + "m");
        size.setText(apply.getSize());
        content.setText(apply.getContent());
        maxReward.setText(apply.getMaxreward()+"");
        reward.setText(apply.getReward());
    }


    //호출취소 버튼 클릭
    public void callCancel(View v){
        //Intent intent = new Intent(getApplicationContext(), ListDetailActivity.class);
        //intent.putExtra("catcher", catcher);



        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<HashMap<String, String>> t = new GenericTypeIndicator<HashMap<String, String>>() {
                };
                catcherlist = dataSnapshot.child("apply").child(applyId).child("catcher").getValue(t);
                applylist = dataSnapshot.child("catcherlist").child(phone).child("apply").getValue(t);
                if (!(catcherlist == null) && !(applylist == null)) {
                    Set<Map.Entry<String, String>> entries1 = catcherlist.entrySet();
                    Set<Map.Entry<String, String>> entries2 = applylist.entrySet();
                    String entrykey1=null;
                    String entrykey2=null;
                    for (Map.Entry<String, String> entry : entries1) {
                        if (entry.getValue().equals(phone)) {
                            entrykey1 = entry.getKey();
                            break;
                        }
                    }
                    for (Map.Entry<String, String> entry : entries2) {
                        if (entry.getValue().equals(applyId)) {
                            entrykey2 = entry.getKey();
                            break;
                        }
                    }
                    if((entrykey1!=null)&&(entrykey2!=null)){
                        ref.child("apply").child(applyId).child("catcherlist").child(entrykey1).removeValue();
                                /*
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                    }
                                });
                                */
                        ref.child("catcherlist").child(phone).child("apply").child(entrykey2).removeValue();
                        Toast.makeText(WaitCallerDetailActivity.this, "호출 취소되었습니다.", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(WaitCallerDetailActivity.this, "해당 호출이 존재하지않습니다.", Toast.LENGTH_SHORT).show();
                }
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
