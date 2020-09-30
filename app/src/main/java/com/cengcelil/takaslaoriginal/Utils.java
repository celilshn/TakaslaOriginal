package com.cengcelil.takaslaoriginal;

import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Utils {
    public static final StorageReference STORAGE_REFERENCE = FirebaseStorage.getInstance().getReference();
    public static void uiOn(ProgressBar progressBar, View view)
    {
        progressBar.setVisibility(View.GONE);
        view.setClickable(true);
        view.setAlpha(1);
    }
    public static void uiOff(ProgressBar progressBar, View view)
    {
        progressBar.setVisibility(View.VISIBLE);
        view.setClickable(false);
        view.setAlpha((float) 0.1);
    }
}
