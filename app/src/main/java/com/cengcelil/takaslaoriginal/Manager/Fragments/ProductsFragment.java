package com.cengcelil.takaslaoriginal.Manager.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.takaslaoriginal.Adapters.ProductsFragmentCategoryAdapter;
import com.cengcelil.takaslaoriginal.Adapters.ProductsFragmentProductAdapter;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.cengcelil.takaslaoriginal.Views.EqualSpaceItemDecoration;
import com.cengcelil.takaslaoriginal.Views.GridSpacingItemDecoration;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.cengcelil.takaslaoriginal.Utils.ACTIVE;
import static com.cengcelil.takaslaoriginal.Utils.FIREBASE_FIRESTORE;

public class ProductsFragment extends Fragment {
    private static final String TAG = "ProductsFragment";
    private RecyclerView rvCategory, rvProduct;
    private ProductsFragmentCategoryAdapter productsFragmentCategoryAdapter;
    private ProductsFragmentProductAdapter productsFragmentProductAdapter;
    private ArrayList<CategoryItem> categoryItems;
    ArrayList<Product> products;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryItems = Utils.getCategoriesHorizontal(getContext());
        productsFragmentCategoryAdapter = new ProductsFragmentCategoryAdapter(categoryItems);
        products = new ArrayList<>();
        productsFragmentProductAdapter = new ProductsFragmentProductAdapter(getContext(),products){
            @Override
            public void onBindViewHolder(@NonNull MyHolder holder, int position) {
                super.onBindViewHolder(holder, position);
            }
        };

        FIREBASE_FIRESTORE.collection(getString(R.string.collection_products))
                .whereEqualTo("activityStatus",Utils.ACTIVE)
                .orderBy("addedTime", Query.Direction.DESCENDING)
                .limit(10)

                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error"+error.getLocalizedMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()) {
                            switch (dc.getType()) {
                                case ADDED:
                                    Log.d(TAG, "New Product: " + dc.getDocument().getData());
                                    Product product = dc.getDocument().toObject(Product.class);
                                    product.setDocumentId(dc.getDocument().getId());
                                    products.add(product);
                                    productsFragmentProductAdapter.notifyDataSetChanged();
                                    Log.d(TAG, "onEvent: "+product.getTitle());
                                    break;
                                case MODIFIED:
                                    Log.d(TAG, "Modified Product: " + dc.getDocument().getData());
                                    break;
                                case REMOVED:
                                    Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                    break;
                            }
                        }
                    }
                });
        Log.d(TAG, "onCreate: "+products.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //SETTİNG VİEWS
        rvCategory = view.findViewById(R.id.products_fragment_category_recyclerview);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        rvCategory.addItemDecoration(new EqualSpaceItemDecoration(10));
        rvCategory.setAdapter(productsFragmentCategoryAdapter);
        rvProduct = view.findViewById(R.id.products_fragment_product_recyclerview);
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2,GridLayoutManager.VERTICAL, false));
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2,20,false));
        rvProduct.setAdapter(productsFragmentProductAdapter);
        productsFragmentProductAdapter.notifyDataSetChanged();
    }
}