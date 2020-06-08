package net.skhu.bugcatcher;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CallerListAdapter extends ArrayAdapter<Apply> {

    Context context;
    ArrayList<Apply> list;
    double c_latitude,c_longitude;
    public CallerListAdapter(Context context, int resource,ArrayList<Apply> list,double c_latitude,double c_longitude){
        super(context,resource);
        this.context=context;
        this.list=list;
        this.c_latitude=c_latitude;
        this.c_longitude=c_longitude;
    }

    @Override
    public int getCount(){
        return list.size();
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Log.d("Apply: ",position+"");
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =inflater.inflate(R.layout.content_caller_list,null,true);
        Apply apply=list.get(position);

        TextView distance =(TextView) convertView.findViewById(R.id.caller_list_distance);
        distance.setText(((int)getDistance(c_latitude,c_longitude,apply.latitude,apply.longitude))+"m");
        //distance.setText(((int)apply.getDistance())+"m");
        TextView maxReward =(TextView) convertView.findViewById(R.id.caller_list_reward_range);
        switch (apply.getMaxreward()) {
            case 5000: maxReward.setText("5000원 이내"); break;
            case 10000: maxReward.setText("5000원-10000원"); break;
            case 15000: maxReward.setText("10000원-15000원"); break;
            case 20000: maxReward.setText("15000원-20000원"); break;
        }

        TextView reward =(TextView) convertView.findViewById(R.id.caller_list_reward);
        reward.setText(apply.getReward());

        return convertView;
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
