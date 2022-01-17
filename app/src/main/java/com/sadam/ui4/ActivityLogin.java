package com.sadam.ui4;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.tabs.TabLayout;
import com.sadam.ui4.Data.MySqLiteOpenHelper;
import com.sadam.ui4.Data.User;

public class ActivityLogin extends AppCompatActivity {
    public static final String SHARED_PREFRENCES_CURRENT_USER = "SHARED_PREFRENCES_UI4_CURRENT_USER";
    private MySqLiteOpenHelper mySqLiteOpenHelper;
    private TabLayout tabLayout;
    private View include_login, include_register;
    private TextView username_login, username_register, passsword_login, password_register;
    private Button btn_login, btn_register;

    public static void saveCurrentUserToSharedPreferences(Context context, User current_user) {
        SharedPreferences.Editor sharedPreferencesEditor = context.getSharedPreferences(SHARED_PREFRENCES_CURRENT_USER, MODE_PRIVATE).edit();
        sharedPreferencesEditor.putLong("id", current_user.getId());
        sharedPreferencesEditor.putString("name", current_user.getName());
        sharedPreferencesEditor.putString("password", current_user.getPassword());
        sharedPreferencesEditor.apply();
    }

    public static User getCurrentUserFromSharedPrefrences(Context context, MySqLiteOpenHelper
            mySqLiteOpenHelper) {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFRENCES_CURRENT_USER, MODE_PRIVATE);
        Long id = preferences.getLong("id", 0);
        String username = preferences.getString("name", null);
        String password = preferences.getString("password", null);
        return new User(mySqLiteOpenHelper, id, username, password);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mySqLiteOpenHelper = new MySqLiteOpenHelper(ActivityLogin.this, MySqLiteOpenHelper.DATABASE_NAME, null, MySqLiteOpenHelper.DATABASE_VERSION);
        tabLayout = findViewById(R.id.tabLayout_login_register);
        include_login = findViewById(R.id.include_login);
        include_register = findViewById(R.id.include_register);
        username_login = findViewById(R.id.username_login);
        username_register = findViewById(R.id.username_register);
        passsword_login = findViewById(R.id.password_login);
        password_register = findViewById(R.id.password_register);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        Button btnPass = findViewById(R.id.button6);
        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                startActivity(intent);
            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        include_login.setVisibility(View.VISIBLE);
                        include_register.setVisibility(View.INVISIBLE);
                        break;
                    case 1:
                        include_login.setVisibility(View.INVISIBLE);
                        include_register.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(1).select();
        tabLayout.getTabAt(0).select();
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_login.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    String password = passsword_login.getText().toString();
                    if (password.equals("")) {
                        Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_LONG).show();
                    } else {
                        User user = User.login(mySqLiteOpenHelper, username, password);
                        if (user == null) {
                            /*登陆失败*/
                        } else {
                            /*登陆成功*/
                            saveCurrentUserToSharedPreferences(ActivityLogin.this, user);
                            Intent intent = new Intent(ActivityLogin.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_register.getText().toString();
                if (username.equals("")) {
                    Toast.makeText(getApplicationContext(), "用户名不能为空！", Toast.LENGTH_LONG).show();
                } else {
                    String password = password_register.getText().toString();
                    if (password.equals("")) {
                        Toast.makeText(getApplicationContext(), "密码不能为空！", Toast.LENGTH_LONG).show();
                    } else {
                        User user = User.register(mySqLiteOpenHelper, username, password);
                        if (user == null) {
                            /*注册失败*/
                        } else {
                            /*注册成功*/
                            tabLayout.getTabAt(0).select();
                        }
                    }
                }
            }
        });
    }
}