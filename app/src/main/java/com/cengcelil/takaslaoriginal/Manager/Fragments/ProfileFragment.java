package com.cengcelil.takaslaoriginal.Manager.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cengcelil.takaslaoriginal.Manager.Profile.MyProductsActivity;
import com.cengcelil.takaslaoriginal.Manager.Profile.SettingsFragment;
import com.cengcelil.takaslaoriginal.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {
    private CircleImageView ivProfilePicure;
    private TextView tvNameSurname;
    private RelativeLayout rlMyProducts,rlSettings,rlInvite,rlProtection,rlDescription,rlInformation,rlLogout;

    private static final String TAG = "ProfileFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews(view);
        setupListeners();
    }

    private void setupListeners() {
        rlMyProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MyProductsActivity.class));
            }
        });
        rlSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment();
                Bundle bundle = new Bundle();
                settingsFragment.setArguments(bundle);
                loadFragment(settingsFragment,getString(R.string.settings_fragment));
            }
        });
    }

    private void setupViews(View view) {
        ivProfilePicure = view.findViewById(R.id.ivProfilePicture);
        rlMyProducts = view.findViewById(R.id.rlMyProducts);
        rlSettings = view.findViewById(R.id.rlSettings);
        rlInvite = view.findViewById(R.id.rlInvite);
        rlProtection = view.findViewById(R.id.rlProtection);
        rlDescription = view.findViewById(R.id.rlDescription);
        rlInformation = view.findViewById(R.id.rlInformation);
        rlLogout = view.findViewById(R.id.rlLogout);

    }
    private void loadFragment(Fragment fragment, String tag) {
        Log.d(TAG, "loadFragment: ");
        // load fragment
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.manager_container, fragment, tag);
        transaction.addToBackStack(null);
        transaction.commit();

    }
}