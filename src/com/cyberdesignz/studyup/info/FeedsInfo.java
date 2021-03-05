package com.cyberdesignz.studyup.info;

import java.io.Serializable;

import com.cyberdesignz.studyup.adapter.FeedsListAdapter;

import android.graphics.Bitmap;

public class FeedsInfo implements Serializable {

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String image;
    private String gender;
    private String birthday;
    private String city;
    private String state;
    private String country;
    private String phone;
    private String join_date;
    private String status;
    private String feed_type;
    private String feed_date;
    private String approves_count;
    private String comments_count;
    private String notedata_id;
    private String notedata_user_id;
    private String notedata_class_id;
    private String notedata_topic;
    private String notedata_subject;
    private String notedata_notetext;
    private String notedata_notesimage;
    private String notedata_audio;
    private String notedata_review;
    private String notedata_dateadded;
    private Bitmap bitmap;
    private String feed_id;

    public String getFeed_id() {
        return feed_id;
    }

    public void setFeed_id(String feed_id) {
        this.feed_id = feed_id;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJoin_date() {
        return join_date;
    }

    public void setJoin_date(String join_date) {
        this.join_date = join_date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFeed_type() {
        return feed_type;
    }

    public void setFeed_type(String feed_type) {
        this.feed_type = feed_type;
    }

    public String getFeed_date() {
        return feed_date;
    }

    public void setFeed_date(String feed_date) {
        this.feed_date = feed_date;
    }

    public String getApproves_count() {
        return approves_count;
    }

    public void setApproves_count(String approves_count) {
        this.approves_count = approves_count;
    }

    public String getComments_count() {
        return comments_count;
    }

    public void setComments_count(String comments_count) {
        this.comments_count = comments_count;
    }

    public String getNotedata_id() {
        return notedata_id;
    }

    public void setNotedata_id(String notedata_id) {
        this.notedata_id = notedata_id;
    }

    public String getNotedata_user_id() {
        return notedata_user_id;
    }

    public void setNotedata_user_id(String notedata_user_id) {
        this.notedata_user_id = notedata_user_id;
    }

    public String getNotedata_class_id() {
        return notedata_class_id;
    }

    public void setNotedata_class_id(String notedata_class_id) {
        this.notedata_class_id = notedata_class_id;
    }

    public String getNotedata_topic() {
        return notedata_topic;
    }

    public void setNotedata_topic(String notedata_topic) {
        this.notedata_topic = notedata_topic;
    }

    public String getNotedata_subject() {
        return notedata_subject;
    }

    public void setNotedata_subject(String notedata_subject) {
        this.notedata_subject = notedata_subject;
    }

    public String getNotedata_notetext() {
        return notedata_notetext;
    }

    public void setNotedata_notetext(String notedata_notetext) {
        this.notedata_notetext = notedata_notetext;
    }

    public String getNotedata_notesimage() {
        return notedata_notesimage;
    }

    public void setNotedata_notesimage(String notedata_notesimage) {
        this.notedata_notesimage = notedata_notesimage;
    }

    public String getNotedata_audio() {
        return notedata_audio;
    }

    public void setNotedata_audio(String notedata_audio) {
        this.notedata_audio = notedata_audio;
    }

    public String getNotedata_review() {
        return notedata_review;
    }

    public void setNotedata_review(String notedata_review) {
        this.notedata_review = notedata_review;
    }

    public String getNotedata_dateadded() {
        return notedata_dateadded;
    }

    public void setNotedata_dateadded(String notedata_dateadded) {
        this.notedata_dateadded = notedata_dateadded;
    }

}
