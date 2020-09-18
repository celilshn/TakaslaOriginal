package com.cengcelil.takaslaoriginal.AddProduct;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cengcelil.takaslaoriginal.AddProduct.Fragments.CaptureFragment;
import com.cengcelil.takaslaoriginal.R;

public class AddProductActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.add_product_container,new CaptureFragment(),getString(R.string.capture_fragment))
                .commit();
    }
}