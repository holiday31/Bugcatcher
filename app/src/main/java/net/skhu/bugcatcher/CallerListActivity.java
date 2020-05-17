package net.skhu.bugcatcher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class CallerListActivity extends AppCompatActivity implements AllCallerListFragment.Listener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caller_list);
    }

    @Override
    public void itemClicked(Catcher catcher){
        Intent intent = new Intent(getApplicationContext(), ListDetailActivity.class);
        intent.putExtra("catcher", catcher);
        startActivity(intent);
    }
}
