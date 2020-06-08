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

    String applyId;
    String phone;
    private String email;
    String name;
    double latitude;
    double longitude;
    double distance;
    //double distance;

    double c_latitude=37.502693;
    double c_longitude=126.4257;
    private CallerListAdapter adapter;
    private ArrayList<Apply> list=new ArrayList<>();
    private ArrayList<String> applyIdList=new ArrayList<>();
    private ArrayList<Double> distanceList=new ArrayList<>();
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();


    static interface Listener{
        void itemClicked(Apply apply,String applyId,int distance);
    }
    private Listener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createApplyList();
        adapter=new CallerListAdapter(inflater.getContext(),0,list,c_latitude,c_longitude);
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
            listener.itemClicked(list.get(position),applyIdList.get(position),(int)Math.round(distanceList.get(position)));
        }
    }

    public void createApplyList(){

        ref.addValueEventListener(new ValueEventListener() {

            float sum;
            int count;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                applyIdList.clear();
                distanceList.clear();
                for (DataSnapshot snapshot1 : dataSnapshot.child("apply").getChildren()) {
                    applyId = snapshot1.getKey();
                    Apply apply= dataSnapshot.child("apply").child(applyId).getValue(Apply.class);

                    //latitude=dataSnapshot.child("apply").child(applyId).child("latitude").getValue(Double.class);
                    //longitude=dataSnapshot.child("apply").child(applyId).child("longitude").getValue(Double.class);

                    //500m이내 거리 사용자인지 확인
                    distance= getDistance(c_latitude,c_longitude,apply.latitude,apply.longitude);
                    if(distance>500)
                        continue;
                    else if (apply.getState()!=0)   //이미 매칭된 상태일 경우
                        continue;
                    list.add(apply);
                    applyIdList.add(applyId);
                    distanceList.add(distance);
                    Log.d("apply Info",apply.getContent());
                    adapter.notifyDataSetChanged();
                }

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




}
