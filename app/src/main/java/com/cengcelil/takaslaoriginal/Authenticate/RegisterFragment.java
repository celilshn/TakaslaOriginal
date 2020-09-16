package com.cengcelil.takaslaoriginal.Authenticate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cengcelil.takaslaoriginal.Manager.ManagerActivity;
import com.cengcelil.takaslaoriginal.Models.UserClient;
import com.cengcelil.takaslaoriginal.Models.UserInformation;
import com.cengcelil.takaslaoriginal.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";
    private EditText etName,etEmail,etPassword,etPassowrdConfirm;
    private String sName,sEmail,sPassword,sPassowrdConfirm;
    private Button btRegister;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firebaseFirestore;
    private CollectionReference usersCollection;
    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        usersCollection = firebaseFirestore.collection(getString(R.string.users_collection));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                getInputFields();
                firebaseAuth.createUserWithEmailAndPassword(sEmail,sPassword)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Log.d(TAG, "onSuccess: AuthResult");
                                final UserInformation userInformation = new UserInformation(sName,sEmail);
                                usersCollection
                                        .document(authResult.getUser().getUid())
                                        .set(userInformation)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d(TAG, "onSuccess: Store");
                                                Toast.makeText(getActivity(), "Kayıt Başarılı", Toast.LENGTH_SHORT).show();
                                                userInformation.setLastLogin(new Date(System.currentTimeMillis()));
                                                ((UserClient)getActivity().getApplicationContext()).setUserInformation(userInformation);
                                                getActivity().finish();
                                                startActivity(new Intent(getActivity(), ManagerActivity.class));

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.d(TAG, "onFailure: Store"+e.getMessage());
                                                Toast.makeText(getActivity(), "Kayıt Başarısız", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG, "onFailure: "+e.getMessage());
                            }
                        });

            }
        });
    }

    private void getInputFields() {
        Log.d(TAG, "getInputFields: ");
        sName = etName.getText().toString().trim();
        sEmail = etEmail.getText().toString().trim();
        sPassword = etPassword.getText().toString().trim();
        sPassowrdConfirm = etPassowrdConfirm.getText().toString().trim();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        setupViews(view);
        return view;
    }
    private void setupViews(View view)
    {
        Log.d(TAG, "setupViews: ");
        etName = view.findViewById(R.id.etName);
        etEmail = view.findViewById(R.id.etEmail);
        etPassword = view.findViewById(R.id.etPassword);
        etPassowrdConfirm = view.findViewById(R.id.etPasswordConfirm);
        btRegister = view.findViewById(R.id.btRegister);
    }

}