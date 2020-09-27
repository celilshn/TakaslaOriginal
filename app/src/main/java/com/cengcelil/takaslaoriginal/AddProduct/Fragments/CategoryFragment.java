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

        categoryItems = new ArrayList<>();
        categoryItems.add(new CategoryItem(R.drawable.tv_png, "Elektronik"));
        categoryItems.add(new CategoryItem(R.drawable.home_stuff_png, "Ev Eşyaları"));
        categoryItems.add(new CategoryItem(R.drawable.toy_png, "Oyuncak Dünyası"));
        categoryItems.add(new CategoryItem(R.drawable.car_spare_part_png, "Oto Yedek Parça"));
        categoryItems.add(new CategoryItem(R.drawable.book_magasine_png, "Kitap ve Dergi"));
        categoryItems.add(new CategoryItem(R.drawable.garden_hobby_png, "Bahçe ve Hobi"));
        categoryItems.add(new CategoryItem(R.drawable.sport_png, "Spor Ekipmanları"));
        categoryItems.add(new CategoryItem(R.drawable.antique_png, "Antika"));
        categoryItems.add(new CategoryItem(R.drawable.office_stuff_png, "Ofis Malzemeleri"));
        categoryItems.add(new CategoryItem(R.drawable.music_png, "Müzik Aletleri"));

        categoryAdapter = new CategoryAdapter(categoryItems) {
            @Override
            public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
                super.onBindViewHolder(holder, position);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(), categoryItems.get(position).getName(), Toast.LENGTH_SHORT).show();
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