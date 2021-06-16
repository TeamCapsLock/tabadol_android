package com.example.tabadol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tabadol.api.MyRoutes;
import com.google.android.material.tabs.TabLayout;

public class OffersActivity extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        setTitle("My offers");
        ViewPager viewPager = findViewById(R.id.viewpager);
        OffersFragmentPagerAdapter offersFragmentPagerAdapter = new OffersFragmentPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(offersFragmentPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.home_menu_item:
                intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                return true;
            case R.id.my_profile_item_menu:
                intent = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(intent);
                return true;
            case R.id.logout_item_menu:
                MyRoutes myRoutes = MyRoutes.getMyRoutesInstanse(this);
                myRoutes.logout();
                finish();
                return true;

            case R.id.myOffers_item_menu:
                MyRoutes.getMyRoutesInstanse(this).getSentOffers();
                MyRoutes.getMyRoutesInstanse(this).getReceivedOffers();
                MyRoutes.getMyRoutesInstanse(this).getFinishedOffers();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent offersIntent = new Intent(OffersActivity.this, OffersActivity.class);
                        startActivity(offersIntent);
                    }
                }, 1500);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}