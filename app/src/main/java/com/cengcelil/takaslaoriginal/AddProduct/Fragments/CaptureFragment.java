package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.hardware.Camera;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.cengcelil.takaslaoriginal.Adapters.CapturedAdapter;
import com.cengcelil.takaslaoriginal.Adapters.CategoryAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.ShowCamera;
import com.cengcelil.takaslaoriginal.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaptureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaptureFragment extends Fragment {
    private static final String TAG = "CaptureFragment";
    private Camera camera;
    private ShowCamera showCamera;
    private FrameLayout flCamera;
    private Camera.PictureCallback pictureCallback;
    private RecyclerView capturedRecycler;
    private CapturedAdapter capturedAdapter;
    private ArrayList<CapturedItem> capturedItems;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CaptureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CaptureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CaptureFragment newInstance(String param1, String param2) {
        CaptureFragment fragment = new CaptureFragment();
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
        capturedItems = new ArrayList<>();
        camera = Camera.open();
        pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d(TAG, "onPictureTaken: ");
                File file = getOutputMediaFiles();
                if(file==null)
                    return;
                else
                {
                    Log.d(TAG, "onPictureTaken: writing");
                    try {
                        capturedItems.add(0,new CapturedItem(file));
                        capturedAdapter.notifyDataSetChanged();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                        camera.startPreview();
                    } catch (IOException e) {
                        Log.d(TAG, "onPictureTaken: failed"+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private File getOutputMediaFiles() {
        String state = Environment.getExternalStorageState();
        if(!state.equals(Environment.MEDIA_MOUNTED))
            return null;
        else
        {
            File folder = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if(folder.exists())
                folder.mkdirs();
            return new File(folder,String.valueOf(System.currentTimeMillis())+".jpg");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_capture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        flCamera = view.findViewById(R.id.flCamera);
        capturedRecycler = view.findViewById(R.id.captured_recyclerview);
        capturedRecycler.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        capturedAdapter = new CapturedAdapter(capturedItems,getActivity());
        capturedRecycler.setAdapter(capturedAdapter);
        showCamera = new ShowCamera(getActivity(),camera);
        view.findViewById(R.id.btcapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                captureImage();
            }
        });
        flCamera.addView(showCamera);
    }

    public void captureImage(){
        if(camera!=null)
        {
            camera.takePicture(null,null,pictureCallback);
        }
    }
    
}