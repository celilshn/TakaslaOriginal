package com.cengcelil.takaslaoriginal.Manager.Products;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.cengcelil.takaslaoriginal.Adapters.SliderAdapter;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.Models.UserInformation;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductPreviewActivity extends AppCompatActivity {
    private static final String TAG = "ProductPreviewActivity";
    private List<StorageReference> storageReferences;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    private Product product;
    private TextView
            tvTitle, tvStatus, tvPrice, tvDescription, tvLocation, tvProfileName, tvRateText;
    private EditText etMessageBox;
    private ImageView
            likeButton
            ,backButton
            ,shareButton;
    private MaterialButton
            followButton, sendButton;
    private RelativeLayout
            reportButton, editableLayout;
    private CircleImageView
            profileImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_preview);
        setupViews();
        setListeners();

        Intent intent = getIntent();
        product = intent.getParcelableExtra("product");
        loadUserDetails();
        loadImages();
        loadDetails();
        loadETWatcher();

    }

    private void setListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                asd
                 */
            }
        });
    }

    private void loadUserDetails() {
        Utils
                .getUsersCollection(this)
                .document(product.getUid())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        UserInformation user = documentSnapshot.toObject(UserInformation.class);
                        if (user != null) {
                            tvProfileName.setText(user.getName());
                   //         tvRateText.setText(String.valueOf(user.getRate()));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: "+e.getLocalizedMessage());
                        Toast.makeText(ProductPreviewActivity.this, "Kullanıcı bilgileri yüklenemedi", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadETWatcher() {
        etMessageBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().isEmpty()) {
                    editableLayout.setAlpha((float) 0.25);
                } else
                    editableLayout.setAlpha(1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void loadDetails() {
        tvTitle.setText(product.getTitle());
        String open = "-(";
        String close = ")";
        tvStatus.setText(open + product.getStatus() + close);
        if (product.isPriceable())
            tvPrice.setText(product.getPrice() + " " + getString(R.string.para_birimi));
        else
            tvPrice.setText(getString(R.string.open_for_exchange));
        tvDescription.setText(product.getDescription());

    }

    private void loadImages() {
        if (product != null) {
            Utils.STORAGE_REFERENCE.child(product.getDocumentId()).listAll()
                    .addOnSuccessListener(new OnSuccessListener<ListResult>() {

                        @Override
                        public void onSuccess(ListResult listResult) {
                            storageReferences = listResult.getItems();
                            sliderAdapter = new SliderAdapter(ProductPreviewActivity.this, storageReferences);
                            sliderView.setSliderAdapter(sliderAdapter);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.getMessage());
                        }
                    });

        }
    }

    private void setupViews() {
        tvTitle = findViewById(R.id.tvProductPreviewTitle);
        tvStatus = findViewById(R.id.tvProductPreviewStatus);
        tvPrice = findViewById(R.id.tvProductPreviewPrice);
        tvDescription = findViewById(R.id.tvProductPreviewDescription);
        tvProfileName = findViewById(R.id.ivProductPreviewProfileName);
        tvRateText = findViewById(R.id.tvProductPreviewRateText);

        followButton = findViewById(R.id.btFollow);
        sendButton = findViewById(R.id.btSendMessage);
        likeButton = findViewById(R.id.btPreviewLike);
        shareButton = findViewById(R.id.btShare);
        backButton = findViewById(R.id.btBack);
        reportButton = findViewById(R.id.btPreviewReport);
        editableLayout = findViewById(R.id.editable_layout);
        etMessageBox = findViewById(R.id.etMessageBox);
        sliderView = findViewById(R.id.imageSlider);

    }
}