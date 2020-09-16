package com.cengcelil.takaslaoriginal.Manager.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cengcelil.takaslaoriginal.Adapters.CategoryAdapter;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchFragment extends Fragment {
    private RecyclerView categoryRecycler;
    private ArrayList<CategoryItem> categoryItems;
    private CategoryAdapter categoryAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        categoryItems = new ArrayList<>();
        categoryRecycler = view.findViewById(R.id.category_recyclerview);
        categoryRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));
        categoryItems.add(new CategoryItem(R.drawable.tv_png,"Elektronik"));
        categoryItems.add(new CategoryItem(R.drawable.home_stuff_png,"Ev Eşyaları"));
        categoryItems.add(new CategoryItem(R.drawable.toy_png,"Oyuncak Dünyası"));
        categoryItems.add(new CategoryItem(R.drawable.car_spare_part_png,"Oto Yedek Parça"));
        categoryItems.add(new CategoryItem(R.drawable.book_magasine_png,"Kitap ve Dergi"));
        categoryItems.add(new CategoryItem(R.drawable.garden_hobby_png,"Bahçe ve Hobi"));
        categoryItems.add(new CategoryItem(R.drawable.sport_png,"Spor Ekipmanları"));
        categoryItems.add(new CategoryItem(R.drawable.antique_png,"Antika"));
        categoryItems.add(new CategoryItem(R.drawable.office_stuff_png,"Ofis Malzemeleri"));
        categoryItems.add(new CategoryItem(R.drawable.music_png,"Müzik Aletleri"));
        categoryAdapter = new CategoryAdapter(categoryItems);
        categoryRecycler.setAdapter(categoryAdapter);
        return view;
    }
}