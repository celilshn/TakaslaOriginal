package com.cengcelil.takaslaoriginal.Manager;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.cengcelil.takaslaoriginal.AddProduct.AddProductActivity;
import com.cengcelil.takaslaoriginal.Manager.Fragments.MessagesFragment;
import com.cengcelil.takaslaoriginal.Manager.Fragments.ProductsFragment;
import com.cengcelil.takaslaoriginal.Manager.Fragments.ProfileFragment;
import com.cengcelil.takaslaoriginal.Manager.Fragments.SearchFragment;
import com.cengcelil.takaslaoriginal.Models.BottomSheetDialog;
import com.cengcelil.takaslaoriginal.Models.UserClient;
import com.cengcelil.takaslaoriginal.Models.UserInformation;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Views.CustomBottomNavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;



public class ManagerActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";
    private FirebaseAuth firebaseAuth;
    private UserInformation userInformation;
    private CustomBottomNavigationView navigationView;
    private CustomBottomNavigationView.OnNavigationItemSelectedListener itemSelectedListener;
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        navigationView = findViewById(R.id.customBottomBar);
        itemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        fragment = new ProductsFragment();
                        loadFragment(fragment, getString(R.string.products_fragment));
                        return true;
                    case R.id.navigation_search:
                        fragment = new SearchFragment();
                        loadFragment(fragment, getString(R.string.search_fragment));
                        return true;
                    case R.id.navigation_messages:
                        fragment = new MessagesFragment();
                        loadFragment(fragment, getString(R.string.messages_fragment));
                        return true;
                    case R.id.navigation_profile:
                        fragment = new ProfileFragment();
                        loadFragment(fragment, getString(R.string.profile_fragment));
                        return true;
                }
                return false;
            }
        };

        navigationView.setOnNavigationItemSelectedListener(itemSelectedListener);
        navigationView.getOrCreateBadge(R.id.navigation_home).setNumber(2);
        userInformation = ((UserClient) getApplicationContext()).getUserInformation();

        findViewById(R.id.btTopbarSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog();
                bottomSheetDialog.show(getSupportFragmentManager(), "s");
            }
        });
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ManagerActivity.this, AddProductActivity.class));
            }
        });

    }

    private void loadFragment(Fragment fragment, String tag) {
        Log.d(TAG, "loadFragment: ");
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.manager_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();

    }

}