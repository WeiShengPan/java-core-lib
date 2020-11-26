package com.pws.poi;

import java.util.Map;

public class TeacherInfo {

    private String date;

    private Map<String, Integer> teacherMap;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Map<String, Integer> getTeacherMap() {
        return teacherMap;
    }

    public void setTeacherMap(Map<String, Integer> teacherMap) {
        this.teacherMap = teacherMap;
    }
}
