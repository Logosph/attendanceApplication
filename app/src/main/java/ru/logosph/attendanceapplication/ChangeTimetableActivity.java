package ru.logosph.attendanceapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Locale;

import ru.logosph.attendanceapplication.databinding.ActivityChangeTimetableBinding;

public class ChangeTimetableActivity extends AppCompatActivity {

    ActivityChangeTimetableBinding binding;
    final private Calendar dateAndTime = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangeTimetableBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ChangeTimetableActivity.this, d,
                        dateAndTime.get(Calendar.YEAR),
                        dateAndTime.get(Calendar.MONTH),
                        dateAndTime.get(Calendar.DAY_OF_MONTH))
                        .show();
            }
        });

        TimetableDatabase timetableDatabase = Room
                .databaseBuilder(
                        this,
                        TimetableDatabase.class,
                        "timetable-database")
                .allowMainThreadQueries()
                .build();



    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime();
        }
    };

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

}