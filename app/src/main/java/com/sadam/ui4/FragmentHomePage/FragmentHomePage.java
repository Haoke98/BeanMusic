package com.sadam.ui4.FragmentHomePage;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.sadam.ui4.Data.Video;
import com.sadam.ui4.MainActivity;
import com.sadam.ui4.MyVideoView;
import com.sadam.ui4.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentHomePage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentHomePage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentHomePage extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private VideoView videoView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Context context;
    private int mVideoWidth, mVideoHeight, showVideoHeight;
    private float scale;
    private MyVideoView myVideoView;
    private int startY, startX, touchRang, screenWidth, screenHeight;
    private String TAG = "SADAM<<<";
    private float maxVolume = 100.0f;
    private float mVolume = 50.0f;
    private Boolean isMute = true;
    private TextView textViewVolume, textViewBrightness;

    public FragmentHomePage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentHomePage.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentHomePage newInstance(String param1, String param2) {
        FragmentHomePage fragment = new FragmentHomePage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public static ArrayList<Video> initVideos() {
        ArrayList<Video> videos = new ArrayList<>();
        videos.add(new Video("https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f400000bvdip1qja8ovg1254o4g&ratio=720p&line=0", 930, 230, 340, "移动应用开发课真牛逼", "this is introuduction", "this is userAvatar usl", "@北邮数字媒体技术"));
        videos.add(new Video("https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f460000bv7nnj99ubsq6lm37pqg&ratio=720p&line=0", 237, 10, 10, "柳杨老师技术好强", "this is introuduction", "this is userAvatar usl", "@Sadam模仿工作室"));
        videos.add(new Video("https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f920000bvdeojgmbijja7qqar50&ratio=720p&line=0", 30, 3, 1, "学号编程很重要", "this is introuduction", "this is userAvatar usl", "@仿真技术"));
        videos.add(new Video("https://aweme.snssdk.com/aweme/v1/playwm/?video_id=v0300f570000bvc7jb605nkillps10t0&ratio=720p&line=0", 530, 30, 120, "编码世界的开头hello World!", "this is introuduction", "this is userAvatar usl", "@BlackHack"));
        return videos;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_home_page, container, false);
        textViewBrightness = view.findViewById(R.id.textview_brightness);
        textViewVolume = view.findViewById(R.id.textview_volume);
        textViewBrightness.setVisibility(View.INVISIBLE);
        textViewVolume.setVisibility(View.INVISIBLE);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview_video);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        VideoAdapter videoAdapter = new VideoAdapter((MainActivity) getActivity());
        recyclerView.setAdapter(videoAdapter);
        videoAdapter.setVideos(initVideos());
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);

//        context = view.getContext();
//        myVideoView = view.findViewById(R.id.videoViewHomePage);
//        String url = ;
////        url = "https://x.izbasarweb.xyz/miniProgram/videoUrlVid=37";
////        url = "http://v3-dy-z.ixigua.com/894ad882618ef66a55db988851f86e13/5fb886ad/video/n/tosedge-tos-agsdqd-ve-0000/48ed4d6d1cd7484791ebd55d80273b9b/?a=1128&amp;br=1971&amp;bt=657&amp;cr=0&amp;cs=0&amp;cv=1&amp;dr=0&amp;ds=3&amp;er=&amp;l=202011211015370101980602133766D9F9&amp;lr=&amp;mime_type=video_mp4&amp;qs=0&amp;rc=M2prOHk5M3NreDMzaGkzM0ApZDNlaTs1NGVmNzc3aTM2OWdeYzVoaGZkaG9fLS0zLTBzcy0zXzM1NDMwMi9hYTA1MDE6Yw%3D%3D&amp;vl=&amp;vr=";
////        Toast.makeText(getBaseContext(), url, Toast.LENGTH_LONG).show();
////        VideoUtils videoUtils = new VideoUtils(new VideoUtils.VideoInformations() {
////            @Override
////            public void dealWithVideoInformation(float w, float h, float vt) {
////
////            }
////        });
////        videoUtils.getVideoWidthAndHeightAndVideoTimes(url);
//        myVideoView.setVideoPath(url);
//        myVideoView.requestFocus();
//        myVideoView.start();

        WindowManager wm = (WindowManager) getActivity()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth = displayMetrics.widthPixels;
        screenHeight = displayMetrics.heightPixels;
        float density = displayMetrics.density;  //屏幕密度
        int densityDpi = displayMetrics.densityDpi;//屏幕密度Dpi
        int screenWidth_dp = (int) (screenWidth / density);
        int screenHeight_dp = (int) (screenHeight / density);

        Log.e("SCREEN_WIDTH_pixel", screenWidth + "|" + screenHeight);
        Log.e("SCREEN_WIDTH_dp", screenWidth_dp + "|" + screenHeight_dp);

//        myVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//            @Override
//            public void onPrepared(MediaPlayer mp) {
//                mp.start();
//                mp.setLooping(true);
////                screenWidth = mp.getVideoWidth();
////                screenHeight = mp.getVideoHeight();
////                mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
////                    @Override
////                    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
////                        //FixMe 获取视频资源的宽度
////                        mVideoWidth = mp.getVideoWidth();
////                        //FixMe 获取视频资源的高度
////                        mVideoHeight = mp.getVideoHeight();
////
////                        scale = (float) mVideoWidth / (float) mVideoHeight;
////                        Toast.makeText(getActivity(),"mp.Width:"+mVideoWidth+" mp.Height:"+mVideoHeight+" w:"+width+" h:"+height,Toast.LENGTH_LONG).show();
////                        myVideoView.getHolder().setFixedSize(width, height);
//////                        refreshPortraitScreen(showVideoHeight == 0 ? DensityUtil.dip2px(context, 300) : showVideoHeight);
////                    }
////                });
////            }
////        });


//        myVideoView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                Log.i(TAG,event.getX()+"|"+event.getY()+"|"+screenWidth+"|"+screenHeight);
//                switch (event.getAction()){
//                    case MotionEvent.ACTION_DOWN:
//                        startY = event.getY();
//                        startX = event.getX();
//                        touchRang = Math.min(screenWidth,screenHeight);
//                        break;
//                    case MotionEvent.ACTION_MOVE:
//                        float endY = event.getY();
//                        float endX = event.getX();
//                        float distanceY = startY-endY;//滑动距离
//                        if(endX<screenWidth/2){//是左边屏幕，调节亮度
//                            Log.e(TAG,"在左半部");
//                            final double FLING_MIN_DISTANCE = 0.5;
//                            final double FLING_MIN_VELOITY = 0.5;
//                            if(distanceY>FLING_MIN_DISTANCE&&Math.abs(distanceY)>FLING_MIN_VELOITY){
//                                Log.e(TAG,"LEFT_UP");
//                                setBrightness(20);
//                            }
//                            if(distanceY<FLING_MIN_DISTANCE&&Math.abs(distanceY)>FLING_MIN_VELOITY){
//                                Log.e(TAG,"LEFT_DOWN");
//                                setBrightness(-20);
//                            }
//                        }else {//是 右半面 调节声音//改变的音量 = （滑动的距离/屏幕宽度）*音量的最大值
//                            float changeVolume = (distanceY/touchRang)*maxVolume;//最终音量 = 原来的音量+改变的音量；
//                            int volume  = (int)Math.min(Math.max(mVolume+changeVolume,0),maxVolume);
//                            if (changeVolume!=0){
//                                isMute = false;
//                                updateVolume(volume,isMute);
//                            }
//                        }
//                        break;
//                    case MotionEvent.ACTION_UP://手指离开
//                        TimerTask timerTask = new TimerTask() {
//                            @Override
//                            public void run() {
//                                textViewBrightness.setVisibility(View.INVISIBLE);
//                                textViewVolume.setVisibility(View.INVISIBLE);
//                            }
//                        };
//                        Timer timer = new Timer();
//                        timer.schedule(timerTask,3*1000);
//                        break;
//                }
//                return true;
//            }
//        });

//         String src = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        WebView webView = view.findViewById(R.id.webview_homepage);
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.loadUrl(src);


////         Uri uri = Uri.parse(src);
////         videoView.setVideoURI(uri);
//        videoView.setVideoPath(src);
//        //videoView.start();
//         videoView.requestFocus();
////
//////        VideoView  videoView = (VideoView) findViewById(R.id.vvMp4);
//        MediaController  mediaController = new MediaController(getActivity());

////        //String uri = "android.resource://" + getPackageName() + "/" + R.raw.big_buck_bunny;
////        videoView.setVideoURI(Uri.parse(uri));
//        videoView.setMediaController(mediaController);
////        mediaController.setMediaPlayer(videoView);
////        videoView.requestFocus();
//////        videoView.start();
//////        videoView.setMediaControer(mediaController);
//////        String url = "http:\\/\\/video.dispatch.tc.qq.com\\/uwMROfz2r57EIaQXGdGn1GddPkb8ztlhqxNOtSjZYCSOV_DK\\/svp_50001\\/szg_27609369_50001_f6fae475390644c28610d9add161c4ba.f622.mp4?vkey=643BF44F18D859B19191A724DF6E4B6AB47CA305D39B0E885B648DDF988E1417A458F4DBAFB8F399B51BAE8825E705D806C503F1E7D85A7951D7E796060680AC491A77A9F0F29E2446559A0841FECCB180C71A468B0D3C80E25B5B9FDD906C4481061D67DB34A2A2299E69E04E4594DE";
//////        String uri = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//////        Uri uri= Uri.parse("android.resource://"+getActivity().getPackageName()+"/raw/video1");
////        String src = "http://mpvideo.qpic.cn/0bf2iqgsyaan3mapjv25grpv2rgdfrca2laa.f10002.mp4?dis_k=c91130cd31eadc33662ad32c6cc158ad&dis_t=1604592575&vid=wxv_1502258508904398851&format_id=10002";
//////        Uri uri = Uri.parse(src);
//////        videoView.setVideoPath(src);
//////        videoView.setMediaController(new MediaController(getActivity()));
//////        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//////            @Override
//////            public void onCompletion(MediaPlayer mp) {
//////                Toast.makeText(getActivity(),"video has been completed.",Toast.LENGTH_LONG).show();
//////            }
//////        });
//////        videoView.setVideoURI(uri);
////        videoView.setVideoPath(src);
////        videoView.requestFocus();
////        videoView.start();
//////        mediaController.setMediaPlayer(videoView);
//        if(!videoView.isPlaying()){
//            videoView.start();
//        }
        final GestureDetector gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Toast.makeText(getContext(), "onDown", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Toast.makeText(getContext(), "onShowPress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Toast.makeText(getContext(), "onSingleTapUp", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Toast.makeText(getContext(), "onScroll", Toast.LENGTH_SHORT).show();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Toast.makeText(getContext(), "onLongPress", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Toast.makeText(getContext(), "onFling", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
//        view.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                return gestureDetector.onTouchEvent(event);
//            }
//        });
        return view;
    }

    //    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//
//        return super.onTouchEvent(event);
//    }
    private void setBrightness(int brightness) {
        Log.e(TAG, "Brightness:" + brightness);
        WindowManager.LayoutParams layoutParams = getActivity().getWindow().getAttributes();
        layoutParams.screenBrightness = layoutParams.screenBrightness + brightness / 255.0f;
        layoutParams.screenBrightness = Math.min(layoutParams.screenBrightness, 1);
        layoutParams.screenBrightness = (float) Math.max(layoutParams.screenBrightness, 0.1);
        getActivity().getWindow().setAttributes(layoutParams);
        if (textViewBrightness != null) {
            textViewBrightness.setVisibility(View.VISIBLE);
            textViewBrightness.setText("当前亮度：" + (int) Math.ceil(layoutParams.screenBrightness * 100) + "%");
        }
    }

    private void updateVolume(int volume, Boolean _isMute) {
        Log.e(TAG, "Volume:" + volume + " isMute:" + isMute);
        textViewVolume.setVisibility(View.VISIBLE);
        textViewVolume.setText("当前音量：" + volume);
    }

    //重新刷新 竖屏显示的大小  树屏显示以宽度为准
    public void refreshPortraitScreen(int height) {
        if (height == 0) {
            height = showVideoHeight;
        }
        if (mVideoHeight > 0 && mVideoWidth > 0) {
            //FixMe 拉伸比例

            mVideoWidth = (int) (height * scale);//FixMe 设置surfaceview画布大小
//            mVideoHeight = (int) (mVideoWidth / scale);
            myVideoView.getHolder().setFixedSize(mVideoWidth, height);
            Log.i("竖屏时视频的宽高==", "宽=" + mVideoWidth + "，高=" + mVideoHeight);
            //FixMe 重绘VideoView大小，这个方法是在重写VideoView时对外抛出方法
//            myVideoView.setMeasure(mVideoWidth, height);
            //FixMe 请求调整
            myVideoView.requestLayout();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
