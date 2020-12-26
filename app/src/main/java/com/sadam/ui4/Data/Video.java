package com.sadam.ui4.Data;

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

    public Video(String url, User user) {
        this.url = url;
        this.user = user;
    }

    public Video(String url, int likeCount, int commentCount, int shareCount, Long id) {
        this.url = url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.id = id;
    }

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

    public Video(String url, int likeCount, int commentCount, int shareCount, String title, String introduction, Long id, User user) {
        this.url = url;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
        this.shareCount = shareCount;
        this.title = title;
        this.introduction = introduction;
        this.id = id;
        this.user = user;
    }

    public Video(String url, int likeCount, int shareCount, String introduction) {
        this.url = url;
        this.likeCount = likeCount;
        this.shareCount = shareCount;
        this.introduction = introduction;
    }

    public Video(String url) {
        this.url = url;
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
}
