package ru.logosph.attendanceapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import ru.logosph.attendanceapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    final private Calendar dateAndTime = Calendar.getInstance();
    TimetableDatabase timetableDatabase;

    @Override
    public void onCreate(Bundle savedInstance) {
        // Initialization view
        super.onCreate(savedInstance);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Setting calendar
        setInitialDateTime();

        // Timetable database
        timetableDatabase = Room
                .databaseBuilder(
                        this,
                        TimetableDatabase.class,
                        "timetable-database")
                .allowMainThreadQueries()
                .build();

        binding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(MainActivity.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        binding.changeTimetableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChangeTimetableActivity.class);
                startActivity(intent);
            }
        });

//        DailyTimetable mon = new DailyTimetable(
//                new String[]{"English", "Physics", "Elem1"}
//        );
//        DailyTimetable tue = new DailyTimetable(
//                new String[]{"Informatics", "Linear algebra", "Mathematical analyzes", "Elem2"}
//        );
//
//        Timetable monday = new Timetable(2, mon.toString());
//        Timetable tuesday = new Timetable(3, tue.toString());
//
//        timetableDatabase.insertAll(monday, tuesday);
//
//        for (int i = 0; i < 7; i++) {
//            try {
//                Log.d("MyMessage", timetableDatabase.getByWeekday(i).subjects);
//            }
//            catch (Exception e){
//                Log.wtf("MyMessage", e.toString());  // NullPointerException
//            }
//        }

        setSpinner();

    }

    // Date And Time
    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
            setSpinner();
        }
    };

    // установка начальных даты и времени
    private void setInitialDateTime() {
        binding.currentDateTime.setText(
                dateAndTime.get(Calendar.DATE) + " " +
                        dateAndTime.getDisplayName(
                                Calendar.MONTH,
                                Calendar.SHORT,
                                Locale.US) + " " +
                        dateAndTime.get(Calendar.YEAR) + " " +
                        dateAndTime.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
        );
    }

    // Set spinner
    private void setSpinner() {
        // Getting timetable from database
        List<Timetable> timetableList = timetableDatabase.timetableDao().getAll();
        int weekday = (dateAndTime.get(Calendar.DAY_OF_WEEK) + 5) % 7;
        String stringReadFromSQLite = "";
        for (Timetable timetable : timetableList) {
            if (timetable.weekday == weekday) {
                stringReadFromSQLite = timetable.subjects;
                break;
            }
        }
        if (!stringReadFromSQLite.isEmpty()) {
            // Setting spinner with data from database
            DailyTimetable day = new DailyTimetable(stringReadFromSQLite);
            String[] items = day.getSubjects();
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    this, R.layout.spinner_item, items);
            binding.spinner.setAdapter(spinnerArrayAdapter);

        } else {
            Toast.makeText(
                            this,
                            "Seems like there are no timetable for this weekday :/",
                            Toast.LENGTH_SHORT)
                    .show();
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(
                    this, R.layout.spinner_item, new String[]{});
            binding.spinner.setAdapter(spinnerArrayAdapter);
        }
    }
}