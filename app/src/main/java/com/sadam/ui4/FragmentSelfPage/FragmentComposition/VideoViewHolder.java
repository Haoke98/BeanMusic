package com.sadam.ui4.FragmentSelfPage.FragmentComposition;

import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sadam.ui4.Data.Video;
import com.sadam.ui4.MainActivity;
import com.sadam.ui4.MyVideoView;
import com.sadam.ui4.R;

public class VideoViewHolder extends RecyclerView.ViewHolder {
    private TextView likeCountView;
    private TextView commentCountView;
    private TextView shareCountView;
    private TextView userNameView;
    private TextView videoTitleView;
    private WebView webView;
    //    private Context context;
    private MyVideoView myVideoView;
    private MainActivity mainActivity;
    private MediaPlayer mediaPlayer;

    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        likeCountView = itemView.findViewById(R.id.texview_likecount);
        commentCountView = itemView.findViewById(R.id.textview_commentcount);
        shareCountView = itemView.findViewById(R.id.textview_sharecount);
        userNameView = itemView.findViewById(R.id.textview_username);
        videoTitleView = itemView.findViewById(R.id.textview_videotitle);
        myVideoView = itemView.findViewById(R.id.myVideoView_video_recyclerview_item_layout);
        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mediaPlayer = mp;
                mp.start();
                mp.setLooping(true);
//                screenWidth = mp.getVideoWidth();
//                screenHeight = mp.getVideoHeight();
//                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                    @Override
//                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//                        //FixMe 获取视频资源的宽度
//                        mVideoWidth = mp.getVideoWidth();
//                        //FixMe 获取视频资源的高度
//                        mVideoHeight = mp.getVideoHeight();
//
//                        scale = (float) mVideoWidth / (float) mVideoHeight;
//                        Toast.makeText(getActivity(),"mp.Width:"+mVideoWidth+" mp.Height:"+mVideoHeight+" w:"+width+" h:"+height,Toast.LENGTH_LONG).show();
//                        myVideoView.getHolder().setFixedSize(width, height);
////                        refreshPortraitScreen(showVideoHeight == 0 ? DensityUtil.dip2px(context, 300) : showVideoHeight);
//                    }
//                });
//            }
//        });

            }
        });
        myVideoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                    } else {
                        mediaPlayer.start();
                    }
                }
                return false;
            }
        });
//        mainActivity = itemView.getContext();
    }

    public void bind(Video video) {
        likeCountView.setText(String.valueOf(video.getLikeCount()));
        commentCountView.setText(String.valueOf(video.getCommentCount()));
        shareCountView.setText(String.valueOf(video.getShareCount()));
        userNameView.setText(video.getUserName());
        videoTitleView.setText(video.getTitle());
//        String url = "https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f460000bv7nnj99ubsq6lm37pqg&ratio=720p&line=0";
//        url = "https://x.izbasarweb.xyz/miniProgram/videoUrlVid=37";
//        url = "http://v3-dy-z.ixigua.com/894ad882618ef66a55db988851f86e13/5fb886ad/video/n/tosedge-tos-agsdqd-ve-0000/48ed4d6d1cd7484791ebd55d80273b9b/?a=1128&amp;br=1971&amp;bt=657&amp;cr=0&amp;cs=0&amp;cv=1&amp;dr=0&amp;ds=3&amp;er=&amp;l=202011211015370101980602133766D9F9&amp;lr=&amp;mime_type=video_mp4&amp;qs=0&amp;rc=M2prOHk5M3NreDMzaGkzM0ApZDNlaTs1NGVmNzc3aTM2OWdeYzVoaGZkaG9fLS0zLTBzcy0zXzM1NDMwMi9hYTA1MDE6Yw%3D%3D&amp;vl=&amp;vr=";
//        Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
//        VideoUtils videoUtils = new VideoUtils(new VideoUtils.VideoInformations() {
//            @Override
//            public void dealWithVideoInformation(float w, float h, float vt) {
//
//            }
//        });
//        videoUtils.getVideoWidthAndHeightAndVideoTimes(url);
        myVideoView.setVideoPath(video.getUrl());
        myVideoView.requestFocus();
        myVideoView.start();

    }
}