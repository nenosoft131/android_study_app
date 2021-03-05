package com.cyberdesignz.studyup.info;

import java.io.Serializable;

public class SubjectInfo implements Serializable {

    String sub_id;
    String sub_name;

    public String getSub_id() {
        return sub_id;
    }

    public void setSub_id(String sub_id) {
        this.sub_id = sub_id;
    }

    public String getSub_name() {
        return sub_name;
    }

    public void setSub_name(String sub_name) {
        this.sub_name = sub_name;
    }

}
