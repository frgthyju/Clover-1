package com.clover.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import com.clover.R;


public class AnniversaryActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener{

    private TextView txt_timeUntilNow, txt_anniversaryDate;//到现在的天数，纪念日的日期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anniversary);

        initToolbar(getResources().getString(R.string.title_activity_anniversary),new Intent(this, MainActivity.class), this);
        txt_timeUntilNow = (TextView)findViewById(R.id.timeUntilNow);
        txt_anniversaryDate = (TextView)findViewById(R.id.anniversaryDate);

        txt_anniversaryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd;
                dpd = DatePickerDialog.newInstance(
                        AnniversaryActivity.this,
                        now.get(Calendar.YEAR),
                        now.get(Calendar.MONTH),
                        now.get(Calendar.DAY_OF_MONTH)
                );
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        monthOfYear = monthOfYear + 1;
        String date = year+"-"+ monthOfYear+"-"+dayOfMonth;
        txt_anniversaryDate.setText(date);
        Calendar now = Calendar.getInstance();
        int yearnow = now.get(Calendar.YEAR);
        int monthnow = now.get(Calendar.MONTH)+1;
        int daynow = now.get(Calendar.DAY_OF_MONTH);
        String nowdate = yearnow+"-"+monthnow+"-"+daynow;
        long timetilnow = getcal(date,nowdate);
        String timeutilnow = Long.toString(timetilnow);
        txt_timeUntilNow.setText(timeutilnow);
    }

    public static long getcal(String time1,String time2){
        long js=0;
        SimpleDateFormat ft=new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date1=ft.parse(time1);
            Date date2=ft.parse(time2);
            js=date2.getTime()-date1.getTime();
            js=js/1000/60/60/24;
        }catch(java.text.ParseException e){
            e.printStackTrace();
        }

        return js;
    }

}
