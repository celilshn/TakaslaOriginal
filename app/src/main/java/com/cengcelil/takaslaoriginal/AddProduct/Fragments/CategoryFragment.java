package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.takaslaoriginal.Adapters.CategoryAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Utils;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private static final String TAG = "CategoryFragment";
    private RecyclerView categoryRecycler;
    private CategoryAdapter categoryAdapter;
    private ArrayList<CategoryItem> categoryItems;
    private ArrayList<CapturedItem> capturedItems;
    private ImageView backButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            capturedItems = getArguments().getParcelableArrayList("capturedItems");
            for (CapturedItem capturedItem : capturedItems)
                Log.d(TAG, "onActivityCreated: " + capturedItem.getFile().getPath());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryRecycler = view.findViewById(R.id.category_fragment_recycler);
        backButton = view.findViewById(R.id.category_top_bar_back_button);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));

        categoryItems = Utils.getCategoriesHorizontal(getActivity());

        categoryAdapter = new CategoryAdapter(categoryItems) {
            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("capturedItems", capturedItems);
                        bundle.putParcelable("categoryItem", categoryItems.get(position));
                        DetailsFragment detailsFragment = new DetailsFragment();
                        detailsFragment.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(R.id.add_product_container, detailsFragment, getString(R.string.details_fragment))
                                .addToBackStack(null).commit();
                    }
                });
            }
        };
        categoryRecycler.setAdapter(categoryAdapter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}