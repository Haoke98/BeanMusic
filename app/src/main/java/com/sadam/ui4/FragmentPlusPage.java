package com.sadam.ui4;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.sadam.ui4.Camera.CameraPreview;

import java.io.File;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentPlusPage.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentPlusPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPlusPage extends Fragment {
    public static final String SHARED_PREFERENCES_KEY_VIDEO_FILE_PATH = "public static final VIDEO_FILE_PATH_SHARED_PREFERENCES";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Camera currActiveCamera;
    private Boolean isFrontCamera = true;
    private CameraPreview cameraPreview;
    private Boolean isRecording = false;
    private FrameLayout frameLayout;
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private Button btnCameraSwitch;
    private MediaRecorder mediaRecorder = null;

    public FragmentPlusPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PlusPageFragement.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentPlusPage newInstance(String param1, String param2) {
        FragmentPlusPage fragment = new FragmentPlusPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View view = inflater.inflate(R.layout.fragment_plus_page_fragement, container, false);
        frameLayout = view.findViewById(R.id.framelayout_for_camerapreview);
        btnCameraSwitch = view.findViewById(R.id.btn_camera_switch);
        /*相机权限在Activity中已经动态申请处理*/
        btnCameraSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int cameraCount = Camera.getNumberOfCameras();
                Toast.makeText(getContext(), "总共有" + cameraCount + "个相机", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < cameraCount; i++) {
                    Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, cameraInfo);
                    if (currActiveCamera != null) {
                        currActiveCamera.stopPreview();
                        currActiveCamera.release();
                        currActiveCamera = null;
                    }
                    if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                        if (isFrontCamera) {
                            /*如果是当前激活的相机是前置的话，就切换到后置*/
                            currActiveCamera = Camera.open(i);
                            break;
                        } else {

                        }
                    } else {
                        if (isFrontCamera) {

                        } else {
                            currActiveCamera = Camera.open(i);
                            break;
                        }
                    }
                }
                cameraPreview = new CameraPreview(getContext(), currActiveCamera);
                frameLayout.addView(cameraPreview);
                isFrontCamera = !isFrontCamera;

            }
        });
        Button btnTakePicture = view.findViewById(R.id.btn_takepictures);
        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isRecording) {
                    stopRecording();
                } else {
                    initMediaRecorder();
                }

//                currActiveCamera.setAutoFocusMoveCallback(new Camera.AutoFocusMoveCallback() {
//                    @Override
//                    public void onAutoFocusMoving(boolean start, Camera camera) {
//                        Camera.Parameters  parameters = currActiveCamera.getParameters();
//                        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
//                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
//                        currActiveCamera.setParameters(parameters);
//
//                    }
//                });
            }

        });
        Button btnInit = view.findViewById(R.id.btn_init_media_recorder);
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initMediaRecorder();
            }
        });
        Button btnPrepared = view.findViewById(R.id.btn_prepare);
        btnPrepared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }

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
//        getActivity().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getActivity().getWindow().setFormat(PixelFormat.TRANSPARENT);
        btnCameraSwitch.callOnClick();
    }

    private void onUnActive() {
        stopRecording();
        releaseCamera();
    }


    private void initMediaRecorder() {
        try {

            currActiveCamera.unlock();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.reset();
            mediaRecorder.setCamera(currActiveCamera);

            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    Log.e("Error on MediaRecorder:", " " + mr.toString() + "what:" + what + " extra:" + extra);
                }
            });
            mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    Log.e("Info on MediaRecorder:", " " + mr.toString() + "what:" + what + " extra:" + extra);
                }
            });
//
            mediaRecorder.setPreviewDisplay(cameraPreview.getHolder().getSurface());

            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);

            mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
            mediaRecorder.setOrientationHint(90);

            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
//            mediaRecorder.setVideoSize(720, 1280);
//            mediaRecorder.setVideoFrameRate(20);
            Log.i("VideoRecorder", "has been initialled ....");
            prepareRecorder();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareRecorder() {
        try {
            File videoOutputFile = new File(Environment.getExternalStorageDirectory().getCanonicalFile() + "/SadamVideo.mp4");
            mediaRecorder.setOutputFile(videoOutputFile.getAbsolutePath());
            Log.e("FilePath:", videoOutputFile.getAbsolutePath());
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.DEFAULT_SHARED_PREFERENCES, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(FragmentPlusPage.SHARED_PREFERENCES_KEY_VIDEO_FILE_PATH, videoOutputFile.getAbsolutePath());
            editor.commit();
            mediaRecorder.prepare();
            Log.i("VideoRecorder", "---开始预览---");
            startRecording();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void startRecording() {
        mediaRecorder.start();
        isRecording = !isRecording;
        btnCameraSwitch.setEnabled(false);
        Log.i("VideoRecorder", "begin recording ....");
    }

    private void stopRecording() {
        if (mediaRecorder != null) {
            mediaRecorder.stop();
            mediaRecorder.reset();
            mediaRecorder.release();
            mediaRecorder = null;
            currActiveCamera.lock();
        }
        isRecording = !isRecording;
        btnCameraSwitch.setEnabled(true);
        Log.i("VideoRecorder", "stop recording ...");
    }

    private void releaseCamera() {
        if (currActiveCamera != null) {
            currActiveCamera.stopPreview();
            currActiveCamera.release();
            currActiveCamera = null;
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
