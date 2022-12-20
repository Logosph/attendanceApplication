package ru.logosph.attendanceapplication;

import androidx.annotation.NonNull;

public class DailyTimetable {
    String[] subjects;

    public DailyTimetable(String[] subjects) {
        this.subjects = subjects;
    }

    public DailyTimetable(String subjects){
        this.subjects = subjects.split(";;");
    }

    @NonNull
    @Override
    public String toString() {
        String joined = "";
        for (String s: subjects){
            joined += s + ";;";
        }
        return joined;
    }

    public String[] getSubjects() {
        return subjects;
    }

    public void setSubjects(String[] subjects) {
        this.subjects = subjects;
    }
}
