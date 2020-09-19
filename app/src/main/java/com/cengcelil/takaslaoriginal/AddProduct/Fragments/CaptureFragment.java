package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.camerakit.type.CameraFlash;
import com.cengcelil.takaslaoriginal.Adapters.CapturedAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class CaptureFragment extends Fragment {
    private static final String TAG = "CaptureFragment";
    private RecyclerView capturedRecycler;
    private CapturedAdapter capturedAdapter;
    private ArrayList<CapturedItem> capturedItems;
    private ImageView turnButton;
    private CameraKitView cameraKitView;
    private ImageView flashButton;
    private static boolean isFlashOn = false;


    @Override
    public void onStart() {
        super.onStart();
        cameraKitView.onStart();
        cameraKitView.setSensorPreset(CameraKit.SENSOR_PRESET_NONE);
        cameraKitView.setImageMegaPixels(10f);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        capturedItems = new ArrayList<>();

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
        cameraKitView = view.findViewById(R.id.camera);//
        flashButton = view.findViewById(R.id.capture_top_bar_flash_button);
// 1
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
                if (cameraKitView.getFacing() == CameraKit.FACING_BACK)
                    cameraKitView.setFlash(CameraKit.FLASH_OFF);
                cameraKitView.toggleFacing();
            }
        });
        flashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                if (cameraKitView.getFacing() == CameraKit.FACING_BACK)
                    if (isFlashOn) {
                        Log.d(TAG, "onClick: flash on to off");
                        isFlashOn = false;
                        flashButton.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.capture_top_bar_flash_on_button));
                        cameraKitView.setFlash(CameraKit.FLASH_OFF);
                    } else {
                        Log.d(TAG, "onClick: flash off to on");
                        isFlashOn = true;
                        flashButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.capture_top_bar_flash_off_button));;
                        cameraKitView.setFlash(CameraKit.FLASH_TORCH);


                    }
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
        cameraKitView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
        cameraKitView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        cameraKitView.onStop();
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
            return new File(folder, System.currentTimeMillis() + ".jpg");
        }
    }


    public void captureImage() {
        Log.d(TAG, "captureImage: ");
        cameraKitView.captureImage(new CameraKitView.ImageCallback() {
            @Override
            public void onImage(CameraKitView cameraKitView, final byte[] bytes) {
                Log.d(TAG, "onImage: ");
                File file = getOutputMediaFiles();
                if (file == null)
                    return;
                else {
                    Log.d(TAG, "onPictureTaken: writing");
                    try {
                        capturedItems.add(0, new CapturedItem(file));
                        capturedAdapter.notifyDataSetChanged();
                        FileOutputStream fos = new FileOutputStream(file);
                        fos.write(bytes);
                        fos.close();
                    } catch (IOException e) {
                        Log.d(TAG, "onPictureTaken: failed" + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}