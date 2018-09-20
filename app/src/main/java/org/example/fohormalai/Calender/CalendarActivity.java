package org.example.fohormalai.Calender;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import org.example.fohormalai.R;

public class CalendarActivity extends AppCompatActivity {

    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        // get action bar
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("My Schedule !");

        // Enabling Up / Back navigation
        actionBar.setDisplayHomeAsUpEnabled(true);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView CalendarView, int year, int month, int dayOfMonth) {
                String date = year + "/" + month + "/"+ dayOfMonth ;
                Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
              /*  Intent intent = new Intent(CalendarActivity.this,MainActivity_Calender.class);
                intent.putExtra("date",date);
                startActivity(intent);
*/
            }
        });

        Button btnNotify = findViewById(R.id.btn_notify);
        btnNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get the token
                String token = FirebaseInstanceId.getInstance().getToken();
                Log.d(TAG, "Token: " + token);
                Toast.makeText(CalendarActivity.this, token, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}