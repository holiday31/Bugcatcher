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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainMapActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        context = this.getBaseContext();

        final TextView mainTitle = findViewById(R.id.toolbar_title);

        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.fragmentMain, new MainFragment());
        fragmentTransaction.commit();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        mDrawerLayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = item.getItemId();
                String title = item.getTitle().toString();

                switch (id) {
                    case R.id.nav_progress:
                        selectFragment(new ProgressFragment());
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_log:
                        selectFragment(new LogFragment());
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_mypage:
                        selectFragment(new MyPageFragment());
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_setting:
                        selectFragment(new SettingFragment());
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(context, title + ": 로그아웃 시도 중", Toast.LENGTH_SHORT).show();
                        selectFragment(new MainFragment());
                        mainTitle.setText("현재 위치");
                        break;
                }
                return true;
            }
        });
    }

    // 왼쪽 상단의 메뉴 버튼 눌렀을 때 드로어가 열린다.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
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

    private void selectFragment(Fragment fragment) {
        // 액티비티 내의 프래그먼트를 관리하려면 FragmentManager를 사용해야 함.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMain, fragment);
        // FragmentTransaction을 변경하고 나면, 반드시 commit()을 호출해야 변경 내용이 적용됨
        fragmentTransaction.commit();
    }
}
