package net.skhu.bugcatcher;

import android.content.Context;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

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
import java.util.Set;

public class WaitCallerListFragment extends ListFragment {
    double c_latitude=37.502693;
    double c_longitude=126.4257;
    String phone="01011111111";
    double distance;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();
    private ArrayList<Apply> list=new ArrayList<>();
    private ArrayList<String> applyIdList=new ArrayList<>();
    HashMap<String,String> applylist;
    private ArrayList<Double> distanceList=new ArrayList<>();
    private CallerListAdapter adapter;


    static interface WaitListener{
        void waitItemClicked(Apply apply,String applyId,int distance);
    }
    private  WaitListener listener;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        createWaitList();
        adapter=new CallerListAdapter(inflater.getContext(),0,list,c_latitude,c_longitude);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        this.listener=(WaitListener)context;
    }

    @Override
    public void onListItemClick(ListView listview, View itemView, int position, long id){
        if(listener!=null){
            listener.waitItemClicked(list.get(position),applyIdList.get(position),(int)Math.round(distanceList.get(position)));
        }
    }


    public void createWaitList(){

        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                applyIdList.clear();
                distanceList.clear();
                GenericTypeIndicator<HashMap<String,String>> t = new GenericTypeIndicator<HashMap<String,String>>() {};
                applylist=dataSnapshot.child("catcherlist").child(phone).child("apply").getValue(t);
                if(!(applylist==null)) {
                    Collection v = applylist.values();
                    Iterator i = v.iterator();
                    while (i.hasNext()) {
                        String applyId=i.next().toString();
                        Apply a = dataSnapshot.child("apply").child(applyId).getValue(Apply.class);
                        if (a.getState()!=0)   //이미 매칭된 상태일 경우
                            continue;
                        distance= getDistance(c_latitude,c_longitude,a.latitude,a.longitude);
                        list.add(a);
                        applyIdList.add(applyId);
                        distanceList.add(distance);
                    }
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
}
