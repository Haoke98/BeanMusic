package com.sadam.ui4;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class SadamFragment extends Fragment {
    @Override
    public void onResume() {
        super.onResume();
        onActive();
        Log.i("Fragment PlusPage", "has been resumed.");
    }

    @Override
    public void onPause() {
        super.onPause();
        onUnActive();
        Log.i("Fragment PlusPage", "has been paused.");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.w("Fragment hidden changed:", " " + hidden);
        if (hidden) {
            Log.d("Fragment", "has been hidden.");
            onUnActive();
        } else {
            Log.d("Fragment", "has been show.");
            onActive();
        }
    }

    public void onActive() {
        Log.d("currentFragment", "has been active.");
    }

    public void onUnActive() {
        Log.d("previewFragment", "has been unactive.");
    }
}
