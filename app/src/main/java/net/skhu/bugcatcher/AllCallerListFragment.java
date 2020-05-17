package net.skhu.bugcatcher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.ListFragment;

public class AllCallerListFragment extends ListFragment {
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
    double c_latitude=37.497793;
    double c_longtitude=126.771798;
    private CatcherListAdapter adapter;
    private ArrayList<Catcher> list=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();


    static interface Listener{
        void itemClicked(Catcher catcher);
    }
    private Listener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createCatcher();
        adapter=new CatcherListAdapter(inflater.getContext(),0,list);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener=(Listener)context;
    }

    @Override
    public void onListItemClick(ListView listview,View itemView,int position,long id){
        if(listener!=null){
            listener.itemClicked(list.get(position));
        }
    }

    public void createCatcher(){

        ref.addValueEventListener(new ValueEventListener() {

            float sum;
            int count;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : dataSnapshot.child("catcherlist").getChildren()) {
//                    Catcher catcher=new Catcher(name,100.02,(float)4.5,2);
//                    for (int i=0;i<5;i++)
//                    list.add(catcher);

                    sum = 0;
                    count = 0;
                    //reviewcount=0;
                    //reviewcount++;

                    phone = snapshot1.getKey();
                    latitude=dataSnapshot.child("catcherlist").child(phone).child("latitude").getValue(Double.class);
                    longtitude=dataSnapshot.child("catcherlist").child(phone).child("longtitude").getValue(Double.class);

                    //500m이내 거리 사용자인지 확인
                    distance= getDistance(c_latitude,c_longtitude,latitude,longtitude);
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
}
