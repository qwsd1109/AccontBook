package com.example.accontbook;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

public class MainActivity extends AppCompatActivity {
    FragmentManager manager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        BottomBar bottomBar = (BottomBar)findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {/*
                switch (tabId){
                    case R.id.tab_home:
                        manager.beginTransaction().replace(R.id.frame_layout, new TotalScreenFrament()).commit();
                        return true;
                    case R.id.tab_pay:
                        manager.beginTransaction().replace(R.id.frame_layout, new ConsumptionFrament()).commit();
                        return true;
                    case R.id.tab_saving:*/
                if(tabId == R.id.tab_home){
                    manager.beginTransaction().replace(R.id.frame_layout, new TotalScreenFrament()).commit();
                }else if(tabId== R.id.tab_pay){
                    manager.beginTransaction().replace(R.id.frame_layout, new ConsumptionFrament()).commit();
                }else if(tabId== R.id.tab_saving){
                    manager.beginTransaction().replace(R.id.frame_layout, new SavingFragment()).commit();
                }
            }
        });
    }
}
