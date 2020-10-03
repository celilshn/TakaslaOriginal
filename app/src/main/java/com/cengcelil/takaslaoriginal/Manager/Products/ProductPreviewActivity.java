package com.cengcelil.takaslaoriginal.Manager.Products;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.cengcelil.takaslaoriginal.Adapters.SliderAdapter;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.List;

public class ProductPreviewActivity extends AppCompatActivity {
    private static final String TAG = "ProductPreviewActivity";
    private List<StorageReference> storageReferences;
    private SliderView sliderView;
    private SliderAdapter sliderAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_preview);
        Intent intent = getIntent();
        Product product = intent.getParcelableExtra("product");
        storageReferences = new ArrayList<>();
        sliderView = findViewById(R.id.imageSlider);

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
}