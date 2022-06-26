package com.edu.ustc.ustcschedule.web;

import java.lang.String;

public class Mycourse {
    String courseName;
    int weekday;
    int startUnit;
    int endUnit;
    String room;


    public Mycourse()
    {
        ;
    }
    public Mycourse(String ans){
        this.courseName = ans;
    }

    public int getWeekday() {
        return weekday;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getRoom() {
        return room;
    }

    public int getStartUnit() {
        return startUnit;
    }

    public int getEndUnit() {
        return endUnit;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setEndUnit(int endUnit) {
        this.endUnit = endUnit;
    }

    public void setStartUnit(int startUnit) {
        this.startUnit = startUnit;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public void setWeekday(int weekday) {
        this.weekday = weekday;
    }
}
