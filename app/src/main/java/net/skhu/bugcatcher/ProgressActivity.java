package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Dictionary;

public class ProgressActivity extends AppCompatActivity {

    private ArrayList<progress> mArrayList;
    private ProgressAdapter mAdapter;
    private int count = -1;

    TextView contact;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress);


        contact = (TextView) findViewById(R.id.contact);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.progressList);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);


        mArrayList = new ArrayList<>();

        mAdapter = new ProgressAdapter(mArrayList);
        mRecyclerView.setAdapter(mAdapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        Button buttonInsert = (Button) findViewById(R.id.button_main_insert);
        buttonInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;

                progress data = new progress("호출완료 ", "접선중 ", "캐치완료 ", count + "");

                //mArrayList.add(0, dict); //RecyclerView의 첫 줄에 삽입
                mArrayList.add(data); // RecyclerView의 마지막 줄에 삽입

                mAdapter.notifyDataSetChanged();
            }
        });
    }


        public void review_clicked(View view) {
            Intent reviewintent = new Intent(this, ReviewActivity.class);
            startActivity(reviewintent);
        }

        //채팅
        /*public void chat_clicked(View view) {
            Intent chatintent = new Intent(this, ChatActivity.class);
            startActivity(chatintent);
        }*/
}