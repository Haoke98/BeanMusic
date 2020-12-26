package com.sadam.ui4.video;

public class Video {
    private String url;
    private int likeCount;
    private int commentCount;
    private int shareCount;
    private String title;
    private String introduction;
    private String userAvatarUrl;
    private String userName;

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
}
