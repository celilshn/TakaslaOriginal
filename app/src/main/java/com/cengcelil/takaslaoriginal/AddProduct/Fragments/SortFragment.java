package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cengcelil.takaslaoriginal.Adapters.SortAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.R;
import com.cengcelil.takaslaoriginal.Views.GridSpacingItemDecoration;

import java.util.ArrayList;

import cn.cyan.dragrecyclerview.DragRecyclerView;
import cn.cyan.dragrecyclerview.HoldTouchHelper;

public class SortFragment extends Fragment {
    private static final String TAG = "SortFragment";
    private DragRecyclerView dragRecyclerView;
    private SortAdapter sortAdapter;
    private ImageView ivPreviewImage;
    private RelativeLayout nextButton,backButton;
    private ArrayList<CapturedItem> capturedItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            capturedItems = getArguments().getParcelableArrayList("capturedItems");
            for (CapturedItem capturedItem : capturedItems)
                Log.d(TAG, "onActivityCreated: " + capturedItem.getFile().getPath());
            sortAdapter = new SortAdapter(capturedItems, getContext());

        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated: ");
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        dragRecyclerView = view.findViewById(R.id.drv);
        ivPreviewImage = view.findViewById(R.id.ivPreviewImage);
        nextButton = view.findViewById(R.id.rlNextButton);
        backButton = view.findViewById(R.id.rlBackButton);
        dragRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3,GridLayoutManager.VERTICAL,false));
        dragRecyclerView.setHasFixedSize(true);
        dragRecyclerView.addItemDecoration(new GridSpacingItemDecoration(3,15,true));
        HoldTouchHelper.OnItemTouchEvent onItem = new HoldTouchHelper.OnItemTouchEvent() {
            @Override
            public void onLongPress(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i) {
                Log.d(TAG, "onLongPress: ");
                if (((SortAdapter) recyclerView.getAdapter()).onItemDrag(i)) {
                    Log.d(TAG, "onLongPress: ");
                    ((DragRecyclerView) recyclerView).startDrag(i);
                    Glide.with(getContext()).
                            load(sortAdapter.getCapturedItems().get(i).getFile()).
                            into(ivPreviewImage);


                }
            }

            @Override
            public void onItemClick(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i) {
                Glide.with(getContext()).
                        load(sortAdapter.getCapturedItems().get(i).getFile()).
                        into(ivPreviewImage);
            }
        };
        dragRecyclerView.dragEnable(true).showDragAnimation(true).setDragAdapter(sortAdapter).bindEvent(onItem);
        dragRecyclerView.setHasFixedSize(false);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               getActivity().onBackPressed();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("capturedItems", capturedItems);
                CategoryFragment categoryFragment = new CategoryFragment();
                categoryFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.add_product_container, categoryFragment, getString(R.string.category_fragment))
                        .addToBackStack(null).commit();
            }
        });
    }

}