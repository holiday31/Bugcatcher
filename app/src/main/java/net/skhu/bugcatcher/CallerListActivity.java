package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CallerListActivity extends AppCompatActivity implements AllCallerListFragment.Listener,WaitCallerListFragment.WaitListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_list);
    }


    @Override
    public void itemClicked(Apply apply,String applyId,int distance){
        Intent intent = new Intent(getApplicationContext(), CallerListDetailActivity.class);
        intent.putExtra("apply", apply);
        intent.putExtra("applyId",applyId);
        intent.putExtra("distance",distance);
        startActivity(intent);
    }

    @Override
    public void waitItemClicked(Apply apply,String applyId,int distance){
        Intent intent = new Intent(getApplicationContext(), WaitCallerDetailActivity.class);
        intent.putExtra("apply", apply);
        intent.putExtra("applyId",applyId);
        intent.putExtra("distance",distance);
        startActivity(intent);
    }


}
