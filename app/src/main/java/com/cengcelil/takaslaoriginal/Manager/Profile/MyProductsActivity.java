package com.cengcelil.takaslaoriginal.Manager.Profile;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.cengcelil.takaslaoriginal.Adapters.MyProductsViewPagerAdapter;
import com.cengcelil.takaslaoriginal.R;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;


public class MyProductsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        MyProductsViewPagerAdapter myProductsViewPagerAdapter = new MyProductsViewPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(myProductsViewPagerAdapter);
        TabLayout tabs = findViewById(R.id.tablayout);
        tabs.setupWithViewPager(viewPager);
        ImageView btBack = findViewById(R.id.myproducts_top_bar_back_button);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}