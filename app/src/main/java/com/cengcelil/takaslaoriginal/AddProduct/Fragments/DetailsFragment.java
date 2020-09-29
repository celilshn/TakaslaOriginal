package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cengcelil.takaslaoriginal.Models.BottomSheetDialog;
import com.cengcelil.takaslaoriginal.Models.BottomSheetMapDialog;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.CategoryItem;
import com.cengcelil.takaslaoriginal.R;

import java.util.ArrayList;

public class DetailsFragment extends Fragment {
    private static final String TAG = "DetailsFragment";
    private ArrayList<CapturedItem> capturedItems;
    private CategoryItem categoryItem;
    private TextView tvPrice,tvExchangerProductInner;
    private EditText etPriceProduct;
    private RadioGroup rgExchange;
    private RelativeLayout details_product_place;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            capturedItems = getArguments().getParcelableArrayList("capturedItems");
             categoryItem = getArguments().getParcelable("categoryItem");
            for (CapturedItem capturedItem : capturedItems)
                Log.d(TAG, "onActivityCreated: " + capturedItem.getFile().getPath());

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvPrice = view.findViewById(R.id.tvPriceProduct);
        rgExchange = view.findViewById(R.id.rgExchange);
        tvExchangerProductInner = view.findViewById(R.id.tvExchangerProductInner);
        etPriceProduct = view.findViewById(R.id.etPriceProduct);
        details_product_place = view.findViewById(R.id.details_product_place);
        rgExchange.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.rb_close_for_exchange)
                {
                  //  tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    etPriceProduct.setBackground(getResources().getDrawable(R.drawable.details_radio_button_unselected_bg));
                    etPriceProduct.setClickable(true);
                    etPriceProduct.setFocusable(true);
                    tvPrice.setPaintFlags(tvPrice.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
                    tvExchangerProductInner.setText(getString(R.string.pricable));
                }
                else
                {
                    etPriceProduct.setBackground(getResources().getDrawable(R.drawable.details_radio_button_selected_bg));
                    etPriceProduct.setClickable(false);
                    etPriceProduct.setFocusable(false);
                    tvPrice.setPaintFlags(tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    tvExchangerProductInner.setText(getString(R.string.open_for_exchange));
                }
            }
        });
        details_product_place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetMapDialog bottomSheetMapDialog= new BottomSheetMapDialog(getFragmentManager());
                bottomSheetMapDialog.setCancelable(false);
                bottomSheetMapDialog.show(getFragmentManager(), "a");
            }
        });


    }
}