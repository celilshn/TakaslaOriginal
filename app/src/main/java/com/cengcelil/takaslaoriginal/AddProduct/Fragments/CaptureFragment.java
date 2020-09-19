package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cengcelil.takaslaoriginal.Adapters.CapturedAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.Models.ShowCamera;
import com.cengcelil.takaslaoriginal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CaptureFragment extends Fragment {
    private static final String TAG = "CaptureFragment";
    private static Camera camera;
    private ShowCamera showCamera;
    private FrameLayout flCamera;
    private Camera.PictureCallback pictureCallback;
    private RecyclerView capturedRecycler;
    private CapturedAdapter capturedAdapter;
    private ArrayList<CapturedItem> capturedItems;
    private ImageView turnButton;
    private boolean isFront = false;
    private View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        capturedItems = new ArrayList<>();
        pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] data, Camera camera) {
                Log.d(TAG, "onPictureTaken: ");
                File file = getOutputMediaFiles();
                if (file == null)
                    return;
                else {
                    Log.d(TAG, "onPictureTaken: writing");
                    try {
                        capturedItems.add(0, new CapturedItem(file));
                        capturedAdapter.notifyDataSetChanged();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(data);
                        fos.close();
                        camera.startPreview();
                    } catch (IOException e) {
                        Log.d(TAG, "onPictureTaken: failed" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");
        return inflater.inflate(R.layout.fragment_capture, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        flCamera = view.findViewById(R.id.flCamera);//  1
        turnButton = view.findViewById(R.id.capture_top_bar_turn_button);// 1
        capturedRecycler = view.findViewById(R.id.captured_recyclerview);// 1

        capturedRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));//  1
        capturedAdapter = new CapturedAdapter(capturedItems, getActivity());//  1
        capturedRecycler.setAdapter(capturedAdapter);//  1
        view.findViewById(R.id.btcapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                captureImage();
            }
        });
        turnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFront)
                    switchToBackCamera();
                else
                    switchToFrontCamera();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        if (camera == null)
            if (isFront)
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
            else
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        showCamera = new ShowCamera(getActivity(), camera);
        flCamera.addView(showCamera);
        showCamera.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        stopCamera();
    }


    private File getOutputMediaFiles() {
        Log.d(TAG, "getOutputMediaFiles: ");
        String state = Environment.getExternalStorageState();
        if (!state.equals(Environment.MEDIA_MOUNTED))
            return null;
        else {
            File folder = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
            if (folder.exists())
                folder.mkdirs();
            return new File(folder, String.valueOf(System.currentTimeMillis()) + ".jpg");
        }
    }


    private void switchToFrontCamera() {
        Log.d(TAG, "switchToFrontCamera: ");
        isFront = true;
        stopCamera();
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_FRONT);
        showCamera.setCamera(camera);
        flCamera.addView(showCamera);
        showCamera.onResume();
    }

    private void switchToBackCamera() {
        Log.d(TAG, "switchToBackCamera: ");
        isFront = false;
        stopCamera();
        camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
        showCamera.setCamera(camera);
        flCamera.addView(showCamera);
        showCamera.onResume();
    }

    public void captureImage() {
        Log.d(TAG, "captureImage: ");
        if (camera != null) {
            camera.takePicture(null, null, pictureCallback);
        }
    }

    public void stopCamera() {
        Log.d(TAG, "stopCamera: ");
        if (camera != null) {
            Log.d(TAG, "stopCamera: camera !=null");
            flCamera.removeAllViews();
            camera.setPreviewCallback(null);
            camera.release();
            camera = null;
        } else
            Log.d(TAG, "stopCamera: camera==null");
    }
}