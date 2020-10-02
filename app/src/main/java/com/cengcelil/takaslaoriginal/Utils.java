package com.cengcelil.takaslaoriginal;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;

import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Utils {
    private static final String TAG = "Utils";

    public static final StorageReference STORAGE_REFERENCE = FirebaseStorage.getInstance().getReference();
    public static final FirebaseFirestore FIREBASE_FIRESTORE = FirebaseFirestore.getInstance();
    public static final String ACTIVE = "active";
    public static final String TIMEOUT = "timeout";
    public static final String SOLD = "sold";
    public static void uiOn(ProgressBar progressBar, View view) {
        progressBar.setVisibility(View.GONE);
        view.setClickable(true);
        view.setAlpha(1);
    }

    public static void uiOff(ProgressBar progressBar, View view) {
        progressBar.setVisibility(View.VISIBLE);
        view.setClickable(false);
        view.setAlpha((float) 0.1);
    }

    public static ArrayList<CategoryItem> getCategoriesHorizontal(Context context) {
        ArrayList<CategoryItem> categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(R.drawable.emlak_png, context.getString(R.string.house_text), 1));
        categoryItems.add(new CategoryItem(R.drawable.vehicle_png, context.getString(R.string.vehicle_text), 2));
        categoryItems.add(new CategoryItem(R.drawable.electronic_png, context.getString(R.string.electronic_text), 3));
        categoryItems.add(new CategoryItem(R.drawable.home_png, context.getString(R.string.home_stuff_text), 4));
        categoryItems.add(new CategoryItem(R.drawable.book_png, context.getString(R.string.book_text), 5));
        categoryItems.add(new CategoryItem(R.drawable.clothes_png, context.getString(R.string.clothes_text), 6));
        categoryItems.add(new CategoryItem(R.drawable.office_png, context.getString(R.string.office_stuff_text), 7));
        categoryItems.add(new CategoryItem(R.drawable.auto_png, context.getString(R.string.auto_part_text), 8));
        categoryItems.add(new CategoryItem(R.drawable.toy_png, context.getString(R.string.toy_text), 9));
        categoryItems.add(new CategoryItem(R.drawable.antique_png, context.getString(R.string.antique_text), 10));
        categoryItems.add(new CategoryItem(R.drawable.music_png, context.getString(R.string.music_stuff_text), 11));
        categoryItems.add(new CategoryItem(R.drawable.garden_png, context.getString(R.string.garden_hobby_text), 12));
        categoryItems.add(new CategoryItem(R.drawable.sport_png, context.getString(R.string.sport_stuff_text), 13));
        categoryItems.add(new CategoryItem(R.drawable.other_png, context.getString(R.string.other_text), 14));
        return categoryItems;
    }

    public static ArrayList<Product> getNewProducts(Context context) {
        final ArrayList<Product> products = new ArrayList<>();
        FIREBASE_FIRESTORE.collection(context.getString(R.string.collection_products))
                .orderBy("addedTime", Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null)
                            for (QueryDocumentSnapshot queryDocumentSnapshot : value) {
                                Product product = queryDocumentSnapshot.toObject(Product.class);
                                product.setDocumentId(queryDocumentSnapshot.getId());
                                products.add(product);
                                Log.d(TAG, "onEvent: " + product.getTitle());
                            }
                        else
                            Log.d(TAG, "onEvent: " + error.getMessage());
                    }
                });
        return products;
    }
}
