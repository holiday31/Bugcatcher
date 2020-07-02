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
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMapActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private Context context;

    // 파이어베이스 인증 객체 생성
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference ref = firebaseDatabase.getReference();

    Switch catcherSwitch;
    boolean switchIsChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_map);

        firebaseAuth = FirebaseAuth.getInstance();

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

//        catcherSwitch = findViewById(R.id.catcher_switch);
//        catcherSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
//                if (isChecked) {    // 켜져있을 때
//                    // The toggle is enabled
//                    switchIsChecked = true; // flag on
//                    ref.child("users").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                } else {    // 꺼져있을 때
//                    // The toggle is disabled
//                    switchIsChecked = false;    // flag off
//                    ref.child("users").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                        }
//                    });
//                }
//            }
//        });

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
                        selectFragment(new ProgressFragment(), switchIsChecked);
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_log:
                        selectFragment(new LogFragment(), switchIsChecked);
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_mypage:
                        selectFragment(new MyPageFragment(), switchIsChecked);
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_setting:
                        selectFragment(new SettingFragment(), switchIsChecked);
                        mainTitle.setText(title);
                        break;
                    case R.id.nav_logout:
                        Toast.makeText(context, title + ": 로그아웃 시도 중", Toast.LENGTH_SHORT).show();
                        selectFragment(new MainFragment(), switchIsChecked);
                        mainTitle.setText("현재 위치");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//        updateUI(currentUser);
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

    private void selectFragment(Fragment fragment, boolean switchFlag) {
        // 액티비티 내의 프래그먼트를 관리하려면 FragmentManager를 사용해야 함.
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentMain, fragment);
        // FragmentTransaction을 변경하고 나면, 반드시 commit()을 호출해야 변경 내용이 적용됨
        fragmentTransaction.commit();

        // 번들객체 생성, flag값 저장
        Bundle bundle = new Bundle();
        bundle.putBoolean("switchFlag", switchFlag);

        // fragment로 번들 전달
        fragment.setArguments(bundle);
    }
}