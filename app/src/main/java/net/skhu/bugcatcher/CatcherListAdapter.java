package net.skhu.bugcatcher;

import android.content.Context;
import android.media.Rating;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class CatcherListAdapter extends ArrayAdapter<Catcher> {

        Context context;
        ArrayList<Catcher> list;
        public CatcherListAdapter(Context context, int resource,ArrayList<Catcher> list){
            super(context,resource);
            this.context=context;
            this.list=list;
        }

        @Override
        public int getCount(){
            return list.size();
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView,@NonNull ViewGroup parent){
            Log.d("adaptercall",position+"");
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.content_catcher_list,null,true);
            Catcher catcher=list.get(position);
            TextView distance =(TextView) convertView.findViewById(R.id.list_distance);
            distance.setText(((int)catcher.getDistance())+"m");
            TextView name =(TextView) convertView.findViewById(R.id.list_name);
            name.setText(catcher.getName());
            TextView score =(TextView) convertView.findViewById(R.id.list_score);
            score.setText(catcher.getScore()+"");
            TextView count =(TextView) convertView.findViewById(R.id.list_scoreCount);
            count.setText("("+catcher.getReviewcount()+")");
            RatingBar ratingBar=convertView.findViewById(R.id.list_star);
            ratingBar.setRating(catcher.getScore());
            return convertView;

        }
}