package com.cengcelil.takaslaoriginal.Authenticate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.cengcelil.takaslaoriginal.Manager.ManagerActivity;
import com.cengcelil.takaslaoriginal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
        if(currentUser==null)
            replaceFragment(new LoginFragment(),getString(R.string.login_fragment));
        else {
            finish();
            startActivity(new Intent(this, ManagerActivity.class));
        }
    }
    private void replaceFragment(Fragment fragment,String tag)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment,tag)
                .commit();
    }
}