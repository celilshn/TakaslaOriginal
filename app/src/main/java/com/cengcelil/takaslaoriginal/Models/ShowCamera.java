package com.cengcelil.takaslaoriginal.Models;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback {
    Camera camera = null;
    SurfaceHolder surfaceHolder;
    private static final String TAG = "ShowCamera";

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: ");
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            Log.d("down", "focusing now");
            if (camera != null)

                camera.autoFocus(null);
        }

        return true;
    }

    public ShowCamera(Context context, Camera camera) {
        super(context);
        Log.d(TAG, "ShowCamera: ");
        this.camera = camera;
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceCreated: ");
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
        Camera.Size size = null;
        int count = 0;
        for (Camera.Size size1 : sizes) {
            Log.i(TAG, "Available resolution: " + size1.width + " " + size1.height);
            if (sizes.size() != 1) {
                if (sizes.indexOf(size1) == sizes.size() / 2) {
                    size = size1;
                    Log.i(TAG, "Choosed: " + size1.width + " " + size1.height);

                    break;
                }
            } else {
                size = size1;
            }
        }

        if (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait");
            camera.setDisplayOrientation(90);
            parameters.setRotation(90);


        } else {
            parameters.set("orientation", "landscape");
            camera.setDisplayOrientation(0);
            parameters.setRotation(0);

        }

        Log.i(TAG, "Chosen resolution: " + size.width + " " + size.height);

        parameters.setPictureSize(size.width, size.height);
        parameters.setPictureFormat(ImageFormat.JPEG);
        parameters.set("jpeg-quality", 100);
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        try {
            camera.setParameters(parameters);
        } catch (Exception e) {
            Log.d(TAG, "surfaceCreated: " + e.getMessage());
        }
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.autoFocus(null);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Log.d(TAG, "surfaceChanged: ");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        Log.d(TAG, "surfaceDestroyed: ");
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
            surfaceHolder.addCallback(null);

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public void onResume() {
        Log.d(TAG, "onResume: ");
        SurfaceHolder previewHolder = getHolder();
        previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        previewHolder.addCallback(this);
    }

}
