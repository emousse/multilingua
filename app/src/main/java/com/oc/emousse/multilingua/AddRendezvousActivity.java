package com.oc.emousse.multilingua;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.os.Build.VERSION;
import android.os.Build.VERSION_CODES;

import com.oc.emousse.multilingua.database.Rendezvous;

import java.util.Calendar;

import io.realm.Realm;

public class AddRendezvousActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnDatePicker, btnTimePicker, btnValidate;
    EditText txtDate, txtTime, txtTitle;
    Realm _realm;
    private int mYear, mMonth, mDay, mHour, mMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rendezvous);

        //set toolbar with back arrow
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        btnDatePicker=(Button)findViewById(R.id.btn_date);
        btnTimePicker=(Button)findViewById(R.id.btn_time);
        btnValidate=(Button)findViewById(R.id.validate_rdv);
        txtDate=(EditText)findViewById(R.id.in_date);
        txtTime=(EditText)findViewById(R.id.in_time);
        txtTitle=(EditText) findViewById(R.id.rdv_title);

        btnDatePicker.setOnClickListener(this);
        btnTimePicker.setOnClickListener(this);
        btnValidate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == btnDatePicker) {

            // Get Current Date
            final Calendar c = Calendar.getInstance();
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {


                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {

                            txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            mYear = year;
                            mMonth = monthOfYear;
                            mDay = dayOfMonth;
                        }
                    }, mYear, mMonth, mDay);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
            datePickerDialog.show();
        }
        if (v == btnTimePicker) {

            // Get Current Time
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            txtTime.setText(hourOfDay + ":" + minute);
                            mHour = hourOfDay;
                            mMinute = minute;
                        }
                    }, mHour, mMinute, false);
            timePickerDialog.show();
        }
        if (v == btnValidate) {
            if(validate()) {
                //create Date object for Rendezvoys bo
                //final Date date = new Date(mYear,mMonth,mDay,mHour,mMinute);

                //set alarm manager
                final Calendar c = Calendar.getInstance();
                c.set(mYear,mMonth,mDay,mHour,mMinute);

                AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                PendingIntent pi = PendingIntent.getBroadcast(this,0,new Intent(this,AlarmReceiver.class),PendingIntent.FLAG_CANCEL_CURRENT);

                if (VERSION.SDK_INT >= VERSION_CODES.KITKAT)
                {
                    //On programme l'alarme avec :
                    //1. un type : dans notre cas l'alarme doit pouvoir réveiller l'application même si le téléphone en en veille (WAKE)
                    //et on se base sur System.currentTimeMillis() pour programmer l'alarme (RTC)
                    //2. On programme l'alarme : dans notre cas c'est dans 10 secondes (timestamp + 10 secondes exprimées en millisecondes)
                    //3. la pending intent
                    am.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 10 * 1000, pi);
                }
                else
                {
                    am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis() + 10 * 1000, pi);
                }

                //commit Rendezvous object in Realm
                _realm = Realm.getDefaultInstance();
                _realm.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        Rendezvous r = _realm.createObject(Rendezvous.class);
                        r.title = txtTitle.getText().toString();
                        r.date = c.getTime();
                    }
                });
                finish();
            } else {
                //show alert dialog or toast
                Toast.makeText(this, "Impossible de créer ce rendez-vous.", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public boolean validate(){
        boolean valid = true;

        String title = txtTitle.getText().toString();

        if (title.isEmpty() || title.length() < 3) {
            txtTitle.setError("Le titre doit comprendre plus de 3 caractères");
            valid = false;
        } else {
            txtTitle.setError(null);
        }

        if (mHour < 8 || mHour > 18){
            valid = false;
            txtTime.setError("L'heure doit être comprise entre 8h et 18h.");
        } else {
            txtTime.setError(null);
        }

        return valid;
    }
}
