package com.cyberdesignz.studyup.info;

import java.io.Serializable;
import java.util.List;

public class ClassInfo implements Serializable {

    String classId;
    String className;
    String professor_name;
    String time_slot;
    List<String> books;
    String subject;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getProfessor_name() {
        return professor_name;
    }

    public void setProfessor_name(String professor_name) {
        this.professor_name = professor_name;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
