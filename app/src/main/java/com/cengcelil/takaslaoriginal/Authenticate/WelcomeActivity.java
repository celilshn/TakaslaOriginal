package com.cengcelil.takaslaoriginal.Authenticate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.cengcelil.takaslaoriginal.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class WelcomeActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        currentUser = firebaseAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        if(currentUser!=null)
            replaceFragment(new LoginFragment(),getString(R.string.login_fragment));
        else
            replaceFragment(new RegisterFragment(),getString(R.string.register_fragment));
    }
    private void replaceFragment(Fragment fragment,String tag)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,fragment,tag)
                .commit();
    }
}