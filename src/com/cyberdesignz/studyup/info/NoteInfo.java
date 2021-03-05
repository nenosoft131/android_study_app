package com.cyberdesignz.studyup.info;

import java.io.Serializable;

public class NoteInfo implements Serializable {


    String id;
    String userId;
    String classId;
    String topic;
    String subject;
    String notesText;
    String notesImage;
    String audioNotes;
    String reviewed;
    String dateAdded;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getNotesText() {
        return notesText;
    }

    public void setNotesText(String notesText) {
        this.notesText = notesText;
    }

    public String getNotesImage() {
        return notesImage;
    }

    public void setNotesImage(String notesImage) {
        this.notesImage = notesImage;
    }

    public String getAudioNotes() {
        return audioNotes;
    }

    public void setAudioNotes(String audioNotes) {
        this.audioNotes = audioNotes;
    }

    public String getReviewed() {
        return reviewed;
    }

    public void setReviewed(String reviewed) {
        this.reviewed = reviewed;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }


}
