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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SortFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SortFragment extends Fragment {
    private static final String TAG = "SortFragment";
    private DragRecyclerView dragRecyclerView;
    private SortAdapter sortAdapter;
    private ImageView ivPreviewImage;
    private RelativeLayout nextButton,backButton;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<CapturedItem> capturedItems;

    public SortFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SortFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SortFragment newInstance(String param1, String param2) {
        SortFragment fragment = new SortFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
    }

}