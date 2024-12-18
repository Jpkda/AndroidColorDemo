package com.example.colors;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.colors.ui.notifications.NotificationsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.colors.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

import android.database.sqlite.SQLiteDatabase;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity implements NotificationsFragment.MyListener {

    private static final String TAG = "MainActivity";
    private ActivityMainBinding binding;
    private String hexcolor;
    private NavigationView main_nv;
    private Class fragmentClass;
    private Fragment nv_fragment, myFragment = null;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ConstraintLayout container;
    private FrameLayout nv_content;


    DBOpenHelper dbOpenHelper;
    SQLiteDatabase sdb;
    ColorBean colorBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // 状态栏字体黑色
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        // 去除标题栏
        this.getSupportActionBar().hide();
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        nv_content = findViewById(R.id.nv_content);
        main_nv = findViewById(R.id.main_nv);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        container = findViewById(R.id.container);
        setupDrawerContent(main_nv);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        View headview=main_nv.inflateHeaderView(R.layout.fragment_header);
        EditText fragment_header_name = headview.findViewById(R.id.fragment_header_name);

        SharedPreferences sharedPreferences = getSharedPreferences("userName", Context.MODE_PRIVATE);///常规模式
        fragment_header_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                } else {
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(fragment_header_name.getWindowToken(), 0);
                    sharedPreferences.edit().putString("username",fragment_header_name.getText().toString()).apply();
                }
            }
        });
        String name = sharedPreferences.getString("username","");
        fragment_header_name.setText(name);

    }


    //myFragment中的接口实现
    public void sendValue(String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        Log.e(TAG,simpleDateFormat.format(date));
        hexcolor = value;
        colorBean = new ColorBean();
        colorBean.setHexcolor(hexcolor);
        dbOpenHelper = new DBOpenHelper(this, "soloColor.db", null, 1);
        dbOpenHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e(TAG,colorBean.getHexcolor());
        values.put("colorHex", hexcolor);
        values.put("time",simpleDateFormat.format(date));
        sdb = dbOpenHelper.getReadableDatabase();
        sdb.insert("colorType",null,values);
        Toast.makeText(this, "保存成功"+colorBean.getHexcolor(), Toast.LENGTH_SHORT).show();
    }
    @SuppressLint("NonConstantResourceId")
    private void seletItemDrawemenu(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.menu_home:
                try {
                    nv_content.setVisibility(View.INVISIBLE);
                } catch (Exception e){
                }
                container.setVisibility(View.VISIBLE);
                fragmentClass = MainActivity.class;
                break;
            case R.id.menu_color_hsv:
                fragmentClass = ColorHsvFragment.class;
                break;
            case R.id.menu_color:
                fragmentClass = CommonColorFragment.class;
                break;
            case R.id.menu_set:
                fragmentClass = SetFragment.class;
                break;
            case R.id.menu_about:
                fragmentClass = AboutFragment.class;
                break;
            case R.id.menu_out:
                System.exit(0);
                fragmentClass = OutFragment.class;
                break;
            default:
                fragmentClass = MainActivity.class;
        } try {
            myFragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            getSupportFragmentManager().beginTransaction().replace(R.id.nv_content,myFragment).commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        menuItem.setChecked(true);
        mDrawerLayout.closeDrawers();
    }

private void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                container.setVisibility(View.INVISIBLE);
                nv_content.setVisibility(View.VISIBLE);
                seletItemDrawemenu(item);
                return true;
            }
        });
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}



