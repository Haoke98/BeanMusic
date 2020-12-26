package com.sadam.ui4;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.tabs.TabLayout;
import com.sadam.ui4.Camera.FragmentPlusPage;
import com.sadam.ui4.Data.MySqLiteOpenHelper;
import com.sadam.ui4.Data.User;
import com.sadam.ui4.FragmentFriendsPage.FragmentFriendsPage;
import com.sadam.ui4.FragmentHomePage.FragmentHomePage;
import com.sadam.ui4.FragmentMessagePage.FragmentMessagesPage;
import com.sadam.ui4.FragmentSelfPage.FragmentSelfPage;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements FragmentHomePage.OnFragmentInteractionListener, FragmentFriendsPage.OnFragmentInteractionListener, FragmentPlusPage.OnFragmentInteractionListener, FragmentSelfPage.OnFragmentInteractionListener, FragmentMessagesPage.OnFragmentInteractionListener {
    public static final String DEFAULT_SHARED_PREFERENCES = "private static final String DEFAULT_SHARED_PREFERENCES_ON_MAIN_ACTIVITY";
    private static final int CAMERA_PREMISSION_REQUEST_CODE = 1;
    private static final int RECORD_AUDIO_PREMISSION_REQUEST_CODE = 2;
    private static final int WRITE_EXTERNAL_STROAGE_PREMISSION_REQUEST_CODE = 3;
    private TabLayout tabLayoutBottom = null;
    private Fragment currentFragment, homePageFragment, friendsPageFragment, plusPageFragment, messagesPageFragment, selfPageFragment;
    private User currentUser;
    private MySqLiteOpenHelper mySqLiteOpenHelper;
    private String[][] PERMISSIONS_REQUESTED = new String[][]{
            new String[]{Manifest.permission.CAMERA, "相机"},
            new String[]{Manifest.permission.RECORD_AUDIO, "音频录制"},
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, "外置文件写入"}

    };

    public static Fragment SadamReplaceFragment(FragmentActivity appCompatActivity, int containerLayout_id, Fragment currentFragment, Fragment targetFragment) {
        FragmentManager fragmentManager = appCompatActivity.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!targetFragment.isAdded()) {
            if (!(currentFragment == null)) {
                transaction.hide(currentFragment);
            }
            transaction.add(containerLayout_id, targetFragment);
        } else {
            transaction.hide(currentFragment);
            transaction.show(targetFragment);
        }
        transaction.commit();
        Log.e("xxx:", targetFragment.getClass().getName());
        return targetFragment;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        for (int i = 0; i < PERMISSIONS_REQUESTED.length; i++) {
            if (requestCode == i) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(), PERMISSIONS_REQUESTED[i][1] + "权限申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "申请" + PERMISSIONS_REQUESTED[i][0] + "权限失败！", Toast.LENGTH_SHORT).show();
                }
            }
        }
//        if(requestCode==CAMERA_PREMISSION_REQUEST_CODE){
//            if (grantResults[0]== PackageManager.PERMISSION_GRANTED){
//                Toast.makeText(getApplicationContext(),"相机权限申请成功",Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(getApplicationContext(),"申请相机权限失败！",Toast.LENGTH_SHORT).show();
//            }
//        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySqLiteOpenHelper = new MySqLiteOpenHelper(MainActivity.this, MySqLiteOpenHelper.DATABASE_NAME, null, MySqLiteOpenHelper.DATABASE_VERSION);
        currentUser = ActivityLogin.getCurrentUserFromSharedPrefrences(MainActivity.this, mySqLiteOpenHelper);
        setContentView(R.layout.activity_main);
        homePageFragment = new FragmentHomePage();
        friendsPageFragment = new FragmentFriendsPage();
        plusPageFragment = new FragmentPlusPage();
        messagesPageFragment = new FragmentMessagesPage();
        selfPageFragment = new FragmentSelfPage();
        tabLayoutBottom = findViewById(R.id.tabLayoutBottom);
        tabLayoutBottom.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        currentFragment = SadamReplaceFragment(MainActivity.this, R.id.frameLayout1, currentFragment, homePageFragment);
                        break;
                    case 1:
                        currentFragment = SadamReplaceFragment(MainActivity.this, R.id.frameLayout1, currentFragment, friendsPageFragment);
                        break;
                    case 2:
                        currentFragment = SadamReplaceFragment(MainActivity.this, R.id.frameLayout1, currentFragment, plusPageFragment);
                        break;
                    case 3:
                        currentFragment = SadamReplaceFragment(MainActivity.this, R.id.frameLayout1, currentFragment, messagesPageFragment);
                        break;
                    case 4:
                        currentFragment = SadamReplaceFragment(MainActivity.this, R.id.frameLayout1, currentFragment, selfPageFragment);
                        break;
                }
//                Toast.makeText(MainActivity.this,"tabPosition:"+tab.getPosition(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        Objects.requireNonNull(tabLayoutBottom.getTabAt(1)).select();
        Objects.requireNonNull(tabLayoutBottom.getTabAt(0)).select();
        checkPermissions();
    }

    private void checkPermissions() {
        for (int i = 0; i < PERMISSIONS_REQUESTED.length; i++) {
            if (checkSelfPermission(PERMISSIONS_REQUESTED[i][0]) == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "已经获取" + PERMISSIONS_REQUESTED[i][1] + "权限", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{PERMISSIONS_REQUESTED[i][0]}, i);
            }
        }
//        if(checkSelfPermission(Manifest.permission.CAMERA)==PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(getApplicationContext(),"已经获取相机权限",Toast.LENGTH_SHORT).show();
//        }else{
//            requestPermissions(new String[]{Manifest.permission.CAMERA},CAMERA_PREMISSION_REQUEST_CODE);
//        }
//        if(checkSelfPermission(Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(getApplicationContext(),"已获取音频录制权限",Toast.LENGTH_SHORT).show();
//        }else{
//            requestPermissions(new String[]{Manifest.permission.RECORD_AUDIO},RECORD_AUDIO_PREMISSION_REQUEST_CODE);
//        }
//        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(getApplicationContext(),"已获取外置文件写入权限",Toast.LENGTH_SHORT).show();
//        }else{
//            requestPermissions(new String[]{Manifest.permission.},RECORD_AUDIO_PREMISSION_REQUEST_CODE);
//        }
    }

    public MySqLiteOpenHelper getMySqLiteOpenHelper() {
        return mySqLiteOpenHelper;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Log.e("xx:", uri.toString());
    }
}
