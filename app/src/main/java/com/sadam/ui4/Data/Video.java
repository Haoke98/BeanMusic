package com.sadam.ui4.Data;

import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.util.Log;

import java.util.HashMap;

public class Video {
    private String url;
    private int likeCount;
    private int commentCount;
    private int shareCount;
    private String title;
    private String introduction;
    private String userAvatarUrl;
    private String userName;
    private Long id;
    private User user;
    private Bitmap cover;

    public Video(String url, int likeCount, int commentCount, int shareCount, String title, String introduction, String userAvatarUrl, String userName, Long id, User user) {
        this.url = url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.title = title;
        this.introduction = introduction;
        this.userAvatarUrl = userAvatarUrl;
        this.userName = userName;
        this.id = id;
        this.user = user;
        this.cover = Video.getVideoThumb(url);
    }

    public Video(String url, User user) {
        this.url = url;
        this.user = user;
    }

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */

    public static Bitmap getVideoThumb(String pathOrUrl) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(pathOrUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("GetVideoThumb(获取视频缩略图)", "用网络视频方法失败");
            try {
                retriever.setDataSource(pathOrUrl);
                bitmap = retriever.getFrameAtTime();
            } catch (Exception e1) {
                e1.printStackTrace();
                Log.w("GetVideoThumb(获取视频缩略图)", "用本地视频方法失败");
            }
        } finally {
            retriever.release();
        }
        return bitmap;

    }

    public Video(String url, int likeCount, int commentCount, int shareCount, String title, String introduction, String userAvatarUrl, String userName) {
        this.url = url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.title = title;
        this.introduction = introduction;
        this.userAvatarUrl = userAvatarUrl;
        this.userName = userName;
    }

    public String getUrl() {
        return url;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public int getShareCount() {
        return shareCount;
    }

    public String getTitle() {
        return title;
    }

    public String getIntroduction() {
        return introduction;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void save() {
        if (user == null) {

        } else {
            if (this.id == null) {
                this.id = this.user.getMySqLiteOpenHelper().insertVideo(user.getId(), this.url);
            }
        }
    }

    public Bitmap getCover() {
        return cover;
    }

    public Bitmap getVideoThumb() {
        return Video.getVideoThumb(this.url);
    }
}
