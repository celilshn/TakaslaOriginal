package com.cengcelil.takaslaoriginal.AddProduct.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.camerakit.CameraKit;
import com.camerakit.CameraKitView;
import com.cengcelil.takaslaoriginal.Adapters.CapturedAdapter;
import com.cengcelil.takaslaoriginal.Models.CapturedItem;
import com.cengcelil.takaslaoriginal.R;
import com.google.android.material.snackbar.Snackbar;
import com.gt.photopicker.PhotoPickerActivity;
import com.gt.photopicker.SelectModel;
import com.gt.photopicker.intent.PhotoPickerIntent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CaptureFragment extends Fragment {
    private static final String TAG = "CaptureFragment";
    private RecyclerView capturedRecycler;
    private CapturedAdapter capturedAdapter;
    private ArrayList<CapturedItem> capturedItems;
    private ImageView turnButton, exitButton;
    private CameraKitView cameraKitView;
    private ImageView flashButton;
    private static boolean isFlashOn = false;
    private LinearLayout btPickGallery;
    private RelativeLayout nextButton;
    public static final int LOUVRE_REQUEST_CODE = 55;
    private static final int maxImagesCount = 9;

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
        btPickGallery = view.findViewById(R.id.btPickGallery);
        turnButton = view.findViewById(R.id.capture_top_bar_turn_button);// 1
        exitButton = view.findViewById(R.id.capture_top_bar_x_button);// 1
        nextButton = view.findViewById(R.id.rlNextButton);// 1

        capturedRecycler = view.findViewById(R.id.captured_recyclerview);// 1
        capturedRecycler.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));//  1
        capturedAdapter = new CapturedAdapter(capturedItems, getActivity());//  1
        capturedRecycler.setAdapter(capturedAdapter);//  1

        view.findViewById(R.id.btcapture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: ");
                if (capturedAdapter.getItemCount() == maxImagesCount) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.activity_gallery_max_selection_reached), Snackbar.LENGTH_SHORT).show();
                } else
                    captureImage();
            }
        });
        btPickGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (capturedAdapter.getItemCount() == maxImagesCount) {
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.activity_gallery_max_selection_reached), Snackbar.LENGTH_SHORT).show();
                } else {
                    PhotoPickerIntent intent = new PhotoPickerIntent(getActivity());
                    intent.setSelectModel(SelectModel.MULTI);
                    intent.setShowCarema(false);
                    intent.setMaxTotal(maxImagesCount-capturedAdapter.getItemCount());
                    startActivityForResult(intent, 999);
                }
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
                        flashButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.capture_top_bar_flash_off_button));
                        cameraKitView.setFlash(CameraKit.FLASH_TORCH);


                    }
            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("capturedItems", capturedItems);
                SortFragment sortFragment = new SortFragment();
                sortFragment.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.add_product_container, sortFragment, getString(R.string.sort_fragment))
                        .addToBackStack(null).commit();
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
                        CapturedItem item = new CapturedItem(file);
                        item.setDragEnable(true);
                        item.setDropEnable(true);
                        capturedItems.add(0, item);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<String> imgPaths = new ArrayList<>();
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 999:
                    imgPaths = data.getStringArrayListExtra(PhotoPickerActivity.EXTRA_RESULT);
                    for (String str : imgPaths) {
                        Log.e("imgPath", str);

                        Log.d(TAG, "onActivityResult: " + str);
                        Uri uri = Uri.parse(str);
                        CapturedItem item = new CapturedItem(new File(uri.getPath()));
                        item.setDragEnable(true);
                        item.setDropEnable(true);
                        capturedItems.add(0, item);

                    }
                    capturedAdapter.notifyDataSetChanged();
                    break;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}