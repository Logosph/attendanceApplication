package ru.logosph.attendanceapplication;

import android.database.sqlite.SQLiteConstraintException;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import java.util.List;

@Database(entities = {Timetable.class}, version = 1)
public abstract class TimetableDatabase extends RoomDatabase {
    public abstract TimetableDao timetableDao();

    public void insertAll(Timetable... timetables) {
        for (Timetable elem : timetables) {
            insert(elem);
        }
    }

    public void insert(Timetable timetable) {
        try {
            this.timetableDao().insertAll(timetable);
        } catch (SQLiteConstraintException e) {
            this.timetableDao().update(timetable);
        }
    }

    public Timetable getByWeekday(int weekday) {
        List<Timetable> timetables = this.timetableDao().getAll();
        for (Timetable elem :
                timetables) {
            if (elem.weekday == weekday) {
                return elem;
            }
        }
        return null;
    }
}
