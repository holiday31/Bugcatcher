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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListDetailActivity extends Activity {
    Catcher catcher;
    TextView distance;
    TextView name;
    TextView reviewcount;
    TextView score;
    ListView listview;
    ListViewAdapter adapter;
    String applyId;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    final ArrayList<String> reviewlist=new ArrayList<>();
    final ArrayList<Float> scorelist=new ArrayList<>();

   // public static final String EXTRA_WORKOUT_ID="id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_list_detail);


        Intent intent = getIntent();
        catcher = (Catcher) intent.getSerializableExtra("catcher");
        applyId=intent.getExtras().getString("applyId");

        distance = (TextView) findViewById(R.id.detail_distance);
        name = (TextView) findViewById(R.id.detail_name);
        reviewcount = (TextView) findViewById(R.id.detail_reviewcount);
        score = (TextView) findViewById(R.id.detail_score);
        listview=(ListView) findViewById(R.id.reviewlist);

        distance.setText((int) (catcher.getDistance()) + "m");
        name.setText(catcher.getName());
        reviewcount.setText(catcher.getReviewcount() + "");
        if (catcher.getReviewcount() == 0)
            score.setText("-");
        else
            score.setText(catcher.getScore() + "");


        ref.child("review").addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.child(catcher.getPhone()).getChildren()) {
                    //String key=snapshot.getKey();
                    reviewlist.add(snapshot.child("review").getValue(String.class));
                    scorelist.add(snapshot.child("starscore").getValue(Float.class));

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled (DatabaseError databaseError){

            }
        });
       adapter=new ListViewAdapter(ListDetailActivity.this,0, reviewlist,scorelist);
        listview.setAdapter(adapter);

        reviewcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listview.getVisibility()==View.VISIBLE)
                    listview.setVisibility(View.GONE);
                else
                    listview.setVisibility(View.VISIBLE);
            }

        });

    }



    private class ListViewAdapter extends ArrayAdapter {
        Context context;
        ArrayList<String> reviewlist;
        ArrayList<Float> scorelist;
        public ListViewAdapter(Context context, int resource,ArrayList<String> reviewlist,ArrayList<Float> scorelist){
            super(context,resource);
            this.context=context;
            this.reviewlist=reviewlist;
            this.scorelist=scorelist;
        }

        @Override
        public int getCount(){
            return reviewlist.size();
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
            LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView =inflater.inflate(R.layout.reviewitem,null,true);
            TextView content =(TextView) convertView.findViewById(R.id.review_item);
            content.setText(reviewlist.get(position));
            RatingBar ratingBar=convertView.findViewById(R.id.ratingBar);
            ratingBar.setRating(scorelist.get(position));
            return convertView;

        }
    }


        //호출 버튼 클릭
    public void callCatcher(View v){
        //Intent intent = new Intent(getApplicationContext(), ListDetailActivity.class);
        //intent.putExtra("catcher", catcher);

        ref.child("catcherlist").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(catcher.getPhone()).child("connect").getValue(Integer.class)==0){
                    ref.child("catcherlist").child(catcher.getPhone()).child("connect").setValue(1);
                    ref.child("apply").child(applyId).child("state").setValue(1);
                    ref.child("apply").child(applyId).child("match").setValue(catcher.getPhone());
                    ref.child("catcherlist").child(catcher.getPhone()).child("match").setValue(applyId);
                    Toast.makeText(ListDetailActivity.this, "매칭 성공!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ListDetailActivity.this, "이미 매칭된 사용자입니다.", Toast.LENGTH_SHORT).show();

//                for (DataSnapshot snapshot : dataSnapshot.child(catcher.getPhone()).getChildren()) {
//                    String key=snapshot.getKey();
//                    reviewlist.add(snapshot.child("review").getValue(String.class));
//                    scorelist.add(snapshot.child("starscore").getValue(Float.class));
//                }

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
