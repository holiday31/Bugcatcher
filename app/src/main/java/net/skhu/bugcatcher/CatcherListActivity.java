package net.skhu.bugcatcher;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
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
        Intent intent = getIntent();
        applyId=intent.getExtras().getString("applyId");

        listview=(ListView) findViewById(R.id.list_view);
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


        SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        email=sharedpreferences.getString("email",null);

        //createCatcher();
        //Log.d("email:",email);
        //CatcherListAdapter adapter=new CatcherListAdapter(this,0,list);
        //listview.setAdapter(adapter);
        Log.d("adaptercreate","------------");
    }

    public void createCatcher(){
//        Catcher catcher=new Catcher(name,100.02,(float)4.5,2);
//        for (int i=0;i<5;i++)
//            list.add(catcher);

        ref.addValueEventListener(new ValueEventListener() {
            float sum;
            int count;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : dataSnapshot.child("apply").child(applyId).child("catcher").getChildren()) {
                    sum = 0;
                    count = 0;

                    phone = snapshot1.getValue(String.class);
                    latitude=dataSnapshot.child("catcherlist").child(phone).child("latitude").getValue(Double.class);
                    longtitude=dataSnapshot.child("catcherlist").child(phone).child("longitude").getValue(Double.class);

                    //500m이내 거리 사용자인지 확인
                    distance= getDistance(c_latitude,c_longitude,latitude,longtitude);
                    if(distance>500)
                        continue;
                    name = dataSnapshot.child("catcherlist").child(phone).child("name").getValue(String.class);
                    Log.d("phoneNumber",phone);
                    //Log.d("catcherName: ",name);
                    //Log.d("count::",count+"");
                    Log.d("reviewcount::",reviewcount+"");


                            for (DataSnapshot snapshot2 : dataSnapshot.child("review").child(phone).getChildren()) {
                                String a = snapshot2.getKey();
                                float star = snapshot2.child("starscore").getValue(Float.class);
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
                    Log.d("Catcher Info",catcher.toString());

                    adapter.notifyDataSetChanged();
               }

            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });


                    //Catcher catcher=new Catcher(name,100.02,4.3,count);
                    //Log.d("사용자",catcher.name+"  "+catcher.distance+"  "+catcher.reviewcount);
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
        createCatcher();
        adapter=new CatcherListAdapter(this,0,list);
        listview.setAdapter(adapter);
    }
    @Override
    public void onStop(){
        super.onStop();
       list.clear();
    }
}
