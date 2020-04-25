package net.skhu.bugcatcher;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainMapActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentMain, new MainFragment());
        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
        }

        // 밑의 네 줄 대신 위의 if 문으로 생성함
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.setDisplayShowTitleEnabled(false);    // 기존 title 지우기
//        actionBar.setDisplayHomeAsUpEnabled(true);  // 메뉴 버튼 만들기
//        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);  // 메뉴 버튼 이미지 지정

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                Fragment fr = new MainFragment();

                int id = item.getItemId();
                String title = item.getTitle().toString();

                if (id == R.id.nav_progress) {
                    Toast.makeText(context, title + ": 진행 사항", Toast.LENGTH_SHORT).show();
                    fr = new ProgressFragment();
                } else if (id == R.id.nav_log) {
                    Toast.makeText(context, title + ": 사용 내역", Toast.LENGTH_SHORT).show();
                    fr = new LogFragment();
                } else if (id == R.id.nav_mypage) {
                    Toast.makeText(context, title + ": 마이페이지", Toast.LENGTH_SHORT).show();
                    fr = new MyPageFragment();
                } else if (id == R.id.nav_setting) {
                    Toast.makeText(context, title + ": 설정", Toast.LENGTH_SHORT).show();
                    fr = new SettingFragment();
                } else if (id == R.id.nav_logout) {
                    Toast.makeText(context, title + ": 로그아웃", Toast.LENGTH_SHORT).show();
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.fragmentMain, fr);
                fragmentTransaction.commit();

                return true;
            }
        });
    }

    // 왼쪽 상단의 메뉴 버튼 눌렀을 때 드로어가 열린다.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
//        switch (item.getItemId()) {
//            case android.R.id.home: {
//
//            }
//        }
        return super.onOptionsItemSelected(item);
    }

    // 드로어가 열렸을 때 뒤로가기 버튼을 누르면 드로어가 닫힌다.
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
        } else {
            super.onBackPressed();
        }
    }
}
