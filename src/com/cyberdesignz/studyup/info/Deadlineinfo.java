package com.cyberdesignz.studyup.info;

import java.io.Serializable;

public class Deadlineinfo implements Serializable {

    String deadline_id;
    String deadline_name;
    String deadline_type;
    String deadline_time;
    String deadline_class_id;
    String deadline_marks;

    public String getDeadline_id() {
        return deadline_id;
    }

    public void setDeadline_id(String deadline_id) {
        this.deadline_id = deadline_id;
    }

    public String getDeadline_name() {
        return deadline_name;
    }

    public void setDeadline_name(String deadline_name) {
        this.deadline_name = deadline_name;
    }

    public String getDeadline_type() {
        return deadline_type;
    }

    public void setDeadline_type(String deadline_type) {
        this.deadline_type = deadline_type;
    }

    public String getDeadline_time() {
        return deadline_time;
    }

    public void setDeadline_time(String deadline_time) {
        this.deadline_time = deadline_time;
    }

    public String getDeadline_class_id() {
        return deadline_class_id;
    }

    public void setDeadline_class_id(String deadline_class_id) {
        this.deadline_class_id = deadline_class_id;
    }

    public String getDeadline_marks() {
        return deadline_marks;
    }

    public void setDeadline_marks(String deadline_marks) {
        this.deadline_marks = deadline_marks;
    }
}
