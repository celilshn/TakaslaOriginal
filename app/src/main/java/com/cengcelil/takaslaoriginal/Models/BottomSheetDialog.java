package com.cengcelil.takaslaoriginal.Models;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.cengcelil.takaslaoriginal.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;


public class BottomSheetDialog extends BottomSheetDialogFragment {
    private static final String TAG = "BottomSheetDialog";
    ViewGroup viewGroup;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable
            ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.exbotsheet,
                container, true);
        this.v=v;
        viewGroup=container;
        Button algo_button = v.findViewById(R.id.btt);
        Log.d(TAG, "onCreateView: "+v+" "+v.getRootView());

        algo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),
                        "Algorithm Shared", Toast.LENGTH_SHORT)
                        .show();
                dismiss();
            }
        });

        return v;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((View) Objects.requireNonNull(getView()).getParent()).setBackgroundColor(Color.TRANSPARENT);



    }
}