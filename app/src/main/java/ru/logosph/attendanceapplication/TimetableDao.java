package ru.logosph.attendanceapplication;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TimetableDao {

    @Insert
    void insertAll(Timetable... timetables);

    @Update
    void update(Timetable timetable);

    @Query("SELECT * FROM timetable")
    List<Timetable> getAll();

}
