package com.sadam.ui4.Data;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

public class User {
    private Long id;
    private String name;
    private String password;
    private MySqLiteOpenHelper mySqLiteOpenHelper;
    private ArrayList<Note> noteArrayList;

    public User(MySqLiteOpenHelper mySqLiteOpenHelper, Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.mySqLiteOpenHelper = mySqLiteOpenHelper;
    }

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public User(MySqLiteOpenHelper mySqLiteOpenHelper, String username, String password) {
        this(mySqLiteOpenHelper, null, username, password);
    }

    public static User login(MySqLiteOpenHelper mySqLiteOpenHelper, String username, String password) {
        Context context = mySqLiteOpenHelper.getContext();
        User user = mySqLiteOpenHelper.getUserByName(username);
        if (user == null) {
            Toast.makeText(context, "用户名：" + username + "不存在！", Toast.LENGTH_LONG).show();
            return null;
        } else {
            if (user.getPassword().equals(password)) {
                Toast.makeText(context, "用户：" + username + "登陆成功！", Toast.LENGTH_LONG).show();
                return user;
            } else {
                Toast.makeText(context, "密码错误！", Toast.LENGTH_LONG).show();
                return null;
            }
        }
    }

    public static User register(MySqLiteOpenHelper mySqLiteOpenHelper, String username, String password) {
        User user = mySqLiteOpenHelper.getUserByName(username);
        if (user == null) {
            user = (new User(mySqLiteOpenHelper, username, password)).save();
            Toast.makeText(mySqLiteOpenHelper.getContext(), "注册成功！", Toast.LENGTH_LONG).show();
            return user;
        } else {
            Toast.makeText(mySqLiteOpenHelper.getContext(), "该用户名已存在，请使用其他的用户名！", Toast.LENGTH_LONG).show();
            return null;
        }
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public MySqLiteOpenHelper getMySqLiteOpenHelper() {
        return mySqLiteOpenHelper;
    }

    public ArrayList<Note> getNoteArrayList() {
        noteArrayList = mySqLiteOpenHelper.getNoteByUser(this);
        return noteArrayList;
    }

    public void newNote(Note note) {
        note.setUser(this);
        note.save();

    }

    public Long getId() {
        return id;
    }

    public User save() {
        if (this.id == null) {
            this.id = mySqLiteOpenHelper.insertUser(name, password);
        } else {
            mySqLiteOpenHelper.updateUser(id, name, password);
        }
        return this;
    }

}
