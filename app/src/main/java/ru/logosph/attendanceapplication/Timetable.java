package ru.logosph.attendanceapplication;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NonNls;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

@Entity
public class Timetable {
    @PrimaryKey(autoGenerate = false)
    public int weekday;

    @ColumnInfo(name="subjects")
    public String subjects;

    public Timetable(int weekday, String subjects) {
        this.weekday = weekday;
        this.subjects = subjects;
    }
}
