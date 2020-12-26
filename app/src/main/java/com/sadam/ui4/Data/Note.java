package com.sadam.ui4.Data;

import android.util.Log;

import java.sql.Timestamp;
import java.util.Date;

public class Note {
    private Long id;
    private User user;
    private String content;
    private Long changedTime;

    /**
     * this constructor is for initial Note for save to databse.
     *
     * @param user    the owner of the Note.
     * @param content the content of the Note(内容）
     */
    public Note(User user, String content) {
        this.user = user;
        this.content = content;
        this.changedTime = getNow();
    }

    /**
     * this constructor is for initial Note from database.
     *
     * @param user
     * @param id
     * @param content
     * @param changedTime
     */
    public Note(com.sadam.ui4.Data.User user, Long id, String content, Long changedTime) {
        this.user = user;
        this.content = content;
        this.changedTime = changedTime;
        this.id = id;
    }

    public static long getNow() {
        Date date = new Date();
        Long stamp = date.getTime();
        return stamp;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(com.sadam.ui4.Data.User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public Long getChangedTime() {
        return changedTime;
    }

    public String getStringChangedTime() {
        Log.e("this is changedTime:", " " + changedTime);
        Timestamp timestamp = new Timestamp(changedTime);
        return timestamp.toString();
    }

    public void save() {
        if (user == null) {
//            Toast.makeText(user.getMySqLiteOpenHelper().getContext,"user 为null时，不能保存任何Note，请先登陆",Toast.LENGTH_LONG).show();
        } else {
            if (this.id == null && changedTime == null) {
                this.id = user.getMySqLiteOpenHelper().insertNote(this);
            } else {
                this.changedTime = user.getMySqLiteOpenHelper().updateNote(this);
            }
        }
    }
}
