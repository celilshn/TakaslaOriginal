package com.cengcelil.takaslaoriginal.Authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cengcelil.takaslaoriginal.Manager.ManagerActivity;
import com.cengcelil.takaslaoriginal.Models.UserClient;
import com.cengcelil.takaslaoriginal.Models.UserInformation;
import com.cengcelil.takaslaoriginal.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private EditText etEmail, etPassword;
    private String sEmail, sPassword;
    private Button btRegister, btLogin, btFacebookLogin;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference userCollection;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        userCollection = firebaseFirestore.collection(getString(R.string.users_collection));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        setupViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getFragmentManager() != null) {
                    getFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, new RegisterFragment(), getString(R.string.register_fragment))
                            .commit();
                }

            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getInputFields();
                firebaseAuth.signInWithEmailAndPassword(sEmail, sPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(final AuthResult authResult) {
                                Log.d(TAG, "onSuccess: ");
                                userCollection.document(authResult.getUser().getUid())
                                        .update("lastLogin", new Date(System.currentTimeMillis()))
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                Log.d(TAG, "onComplete: ");
                                                setUserInformation(authResult.getUser().getUid());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: " + e.getMessage());
                                            }
                                        });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: " + e.getMessage());
                            }
                        });
            }
        });
    }

    private void setUserInformation(String uid) {
        Log.d(TAG, "setUserInformation: ");
        userCollection.document(uid).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.d(TAG, "onComplete: ");
                        ((UserClient) getActivity().getApplicationContext()).setUserInformation(task.getResult().toObject(UserInformation.class));
                        Log.d(TAG, "onComplete: "+((UserClient) getActivity().getApplicationContext()).getUserInformation().getName());
                        getActivity().finish();
                        startActivity(new Intent(getActivity(), ManagerActivity.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getMessage());
                    }
                });
    }

    private void setupViews(View view) {
        Log.d(TAG, "setupViews: ");
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        btRegister = view.findViewById(R.id.btRegister);
        btLogin = view.findViewById(R.id.btLogin);
        btFacebookLogin = view.findViewById(R.id.btFacebookLogin);
    }

    private void getInputFields() {
        Log.d(TAG, "getInputFields: ");
        sEmail = etEmail.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
    }


}