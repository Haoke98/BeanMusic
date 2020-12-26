package com.sadam.ui4;

import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.HashMap;

public class VideoUtils {
    private VideoInformations videoInformations;

    public VideoUtils(VideoInformations videoInformations) {
        this.videoInformations = videoInformations;
    }

    //获取视频的宽高,和时长
    protected void getVideoWidthAndHeightAndVideoTimes(String videoUrl) {
        final MediaMetadataRetriever mediaMetadataRetriever = new MediaMetadataRetriever();
        mediaMetadataRetriever.setDataSource(videoUrl, new HashMap<String, String>());
        new Thread() {
            @Override
            public void run() {
                float videoTimes = 0;
                float videoWidth = 0;
                float videoHeight = 0;
                super.run();
                try {
                    videoTimes = Float.parseFloat(mediaMetadataRetriever.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION));
                    videoWidth = Float.parseFloat(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH));
                    videoHeight = Float.parseFloat(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT));
                } catch (Exception e) {
                    videoTimes = 0;
                    videoWidth = 0;
                    videoHeight = 0;
                } finally {
                    Log.i("zzm", "视频的宽：  " + videoWidth);
                    Log.i("zzm", "视频的高：  " + videoHeight);
                    Log.i("zzm", "视频的长度：  " + videoTimes);
                    mediaMetadataRetriever.release();
                    videoInformations.dealWithVideoInformation(videoWidth, videoHeight, videoTimes);
                }
            }
        }.start();
    }

    interface VideoInformations {
        void dealWithVideoInformation(float w, float h, float vt);
    }
}