package com.cyberdesignz.studyup.info;

import java.io.Serializable;

import com.cyberdesignz.studyup.utils.ImageLoader;

public class CommentsInfo implements Serializable {

    private String comment_id;
    private String feed_id;
    private String user_id;
    private String comment;
    ImageLoader imageloader;
    private String date_commented;
    private String commentUser_image;
    private String commentUser_name;
    private String commentuser_email;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate_commented() {
        return date_commented;
    }

    public void setDate_commented(String date_commented) {
        this.date_commented = date_commented;
    }

    public class Hold {

    }

    public String getCommentUser_image() {
        return commentUser_image;
    }

    public void setCommentUser_image(String commentUser_image) {
        this.commentUser_image = commentUser_image;
    }

    public String getCommentUser_name() {
        return commentUser_name;
    }

    public void setCommentUser_name(String commentUser_name) {
        this.commentUser_name = commentUser_name;
    }

    public String getCommentuser_email() {
        return commentuser_email;
    }

    public void setCommentuser_email(String commentuser_email) {
        this.commentuser_email = commentuser_email;
    }
}
