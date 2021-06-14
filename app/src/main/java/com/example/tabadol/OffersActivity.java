package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class OffersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        ViewPager viewPager = findViewById(R.id.viewpager);
        OffersFragmentPagerAdapter offersFragmentPagerAdapter = new OffersFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(offersFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
}