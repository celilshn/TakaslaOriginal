package com.cengcelil.takaslaoriginal.Manager.Profile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.cengcelil.takaslaoriginal.Adapters.ProductsFragmentProductAdapter;
import com.cengcelil.takaslaoriginal.Models.Product;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;
import com.cengcelil.takaslaoriginal.Views.GridSpacingItemDecoration;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static com.cengcelil.takaslaoriginal.Utils.FIREBASE_FIRESTORE;

public class LikedFragment extends Fragment {
    private static final String TAG = "LikedFragment";
    private RecyclerView rvProduct;
    private ProductsFragmentProductAdapter productsFragmentProductAdapter;
    ArrayList<Product> products;
    private RelativeLayout rlNoProduct;
    private ProgressBar progressBar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        products = new ArrayList<>();
        productsFragmentProductAdapter = new ProductsFragmentProductAdapter(getContext(), products);
        FIREBASE_FIRESTORE.collection(getString(R.string.collection_products))
                .whereEqualTo("activityStatus", Utils.ACTIVE)
                .orderBy("addedTime", Query.Direction.DESCENDING)
                .limit(10)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {
                            Log.w(TAG, "listen:error", error);
                            return;
                        }
                        if (value != null) {
                            if (value.getDocumentChanges().size() != 0) {
                                for (DocumentChange dc : value.getDocumentChanges()) {
                                    switch (dc.getType()) {
                                        case ADDED:
                                            Log.d(TAG, "New Product: " + dc.getDocument().getData());
                                            Product product = dc.getDocument().toObject(Product.class);
                                            if (product.getIsLikedFrom().contains(FirebaseAuth.getInstance().getUid()))
                                            {
                                                product.setDocumentId(dc.getDocument().getId());
                                                products.add(product);
                                                productsFragmentProductAdapter.notifyDataSetChanged();
                                            }
                                            Log.d(TAG, "onEvent: " + product.getTitle());
                                            break;
                                        case MODIFIED:
                                            Log.d(TAG, "Modified Product: " + dc.getDocument().getData());
                                            break;
                                        case REMOVED:
                                            Log.d(TAG, "Removed city: " + dc.getDocument().getData());
                                            break;
                                    }
                                }
                                progressBar.setVisibility(View.GONE);
                                if(products.size()==0)
                                    rlNoProduct.setVisibility(View.VISIBLE);
                                else
                                    rlNoProduct.setVisibility(View.GONE);


                            } else {
                                rlNoProduct.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_liked, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProduct = view.findViewById(R.id.liked_fragment_product_recyclerview);
        rlNoProduct = view.findViewById(R.id.rlNoProduct);
        progressBar = view.findViewById(R.id.spin_kit);
        progressBar.setIndeterminateDrawable(new WanderingCubes());
        rvProduct.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
        rvProduct.addItemDecoration(new GridSpacingItemDecoration(2, 20, false));
        rvProduct.setAdapter(productsFragmentProductAdapter);
        productsFragmentProductAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: " + products.size());
        progressBar.setVisibility(View.GONE);
        if (products.size() != 0) {
            rlNoProduct.setVisibility(View.GONE);
        } else
            rlNoProduct.setVisibility(View.VISIBLE);
    }
}