package com.sadam.ui4.Camera;

import android.hardware.Camera;

public class CameraTools {
    public static Camera getCameraInstance(int i) {
        Camera c = null;
        try {
            c = Camera.open(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return c;
    }
}
