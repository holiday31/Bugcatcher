package net.skhu.bugcatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatcherListActivity extends AppCompatActivity {
    private ListView listview;
    String phone;
    private String email;
    String name;
    double latitude;
    double longtitude;
    double distance;
    //double distance;
    //double score;
    float average;
    int reviewcount=0;
    static boolean calledAlready = false;
    double c_latitude=37.502693;
    double c_longitude=126.4257;
    String applyId;
    CatcherListAdapter adapter;

    private ArrayList<Catcher> list=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catcher_list);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            email=user.getEmail();
            Log.d("user",email);
        } else {
            // No user is signed in
            Log.d("user","no user");
        }

        Intent intent = getIntent();
        if(intent != null) {//푸시알림을 선택해서 실행한것이 아닌경우 예외처리
            String notificationData = intent.getStringExtra("test");
            if(notificationData != null)
                Log.d("FCM_TEST", notificationData);
        }



        //Intent intent = getIntent();
        //applyId=intent.getExtras().getString("applyId");

        createCatcherList();

        listview=(ListView) findViewById(R.id.list_view);
        adapter=new CatcherListAdapter(this,0,list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Catcher catcher=list.get(position);

                Intent intent = new Intent(getApplicationContext(), ListDetailActivity.class);
                intent.putExtra("catcher", catcher);
                intent.putExtra("applyId", applyId);
                startActivity(intent);
            }
        });

        //createCatcher();
        //Log.d("email:",email);

        Log.d("adaptercreate","------------");
    }

    public void createCatcherList(){
        ref.addValueEventListener(new ValueEventListener() {
            float sum;
            int count;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot : dataSnapshot.child("apply").getChildren()) {

                    sum=0;
                    count=0;
                    Log.d("email", email);
                    //Apply apply=snapshot.child(snapshot.getKey()).getValue(Apply.class);
                    //Log.d("iii",snapshot.getKey());
                    String email2 = snapshot.child("email").getValue(String.class);
                    //Log.d("email2",email2);

                    if ((email.equals(email2)) && (snapshot.child("state").getValue(Integer.class) == 0)) {
                        applyId = snapshot.getKey();
                        for (DataSnapshot snapshot2 : snapshot.child("catcher").getChildren()) {
                            String phone=snapshot2.getValue(String.class);
                            Log.d("phone:",phone);
                            String name=dataSnapshot.child("catcherlist").child(phone).child("name").getValue(String.class);
                            latitude=dataSnapshot.child("catcherlist").child(phone).child("latitude").getValue(Double.class);
                            longtitude=dataSnapshot.child("catcherlist").child(phone).child("longitude").getValue(Double.class);

                            distance= getDistance(c_latitude,c_longitude,latitude,longtitude);

                            for (DataSnapshot snapshot3 : dataSnapshot.child("review").child(phone).getChildren()) {
                                String a = snapshot3.getKey();
                                float star = snapshot3.child("starscore").getValue(Float.class);
                                count++;
                                sum+=star;

                                Log.d("키 값", a);
                                Log.d("별점", a+" -"+star + "");
                                Log.d("카운트", count+"");
                                Log.d("합", sum+"");

                            }


                            if(count>0)
                                average=sum/count;
                            else
                                average=0;
                            Catcher catcher=new Catcher(phone,name,distance,average,count);
                            list.add(catcher);
                        }

                        break;
                    }


                }

                if (applyId == null) {
                    Toast.makeText(CatcherListActivity.this, "호출이 존재하지않습니다.", Toast.LENGTH_SHORT).show();
                    Intent mapIntent = new Intent(CatcherListActivity.this, MainMapActivity.class);
                    startActivity(mapIntent);
                }
                adapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });

    }



    //미터 단위
    public double getDistance(double lat1 , double lng1 , double lat2 , double lng2 ){
        double distance;

        Location locationA = new Location("point A");
        locationA.setLatitude(lat1);
        locationA.setLongitude(lng1);

        Location locationB = new Location("point B");
        locationB.setLatitude(lat2);
        locationB.setLongitude(lng2);

        distance = locationA.distanceTo(locationB);

        return distance;
    }

    @Override
    public void onStart(){
        super.onStart();

    }
    @Override
    public void onStop(){
        super.onStop();
       list.clear();
    }
}
