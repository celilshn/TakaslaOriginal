package com.cengcelil.takaslaoriginal.Models;

import android.widget.ImageView;

import java.io.File;

public class CapturedItem {

    File file;
    public File getFile() {
        return file;
    }

    public CapturedItem(File file) {
        this.file = file;
    }
}
