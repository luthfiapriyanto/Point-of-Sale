package com.example.luthf.pointofsale;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.luthf.pointofsale.Adapter.*;

import java.util.Calendar;

public class Report extends AppCompatActivity {
    private TextView tvDisplayDate, tvDisplayDate2, tvDisplayDate3;
    private DatePicker dpResult;
    private Button btnChangeDate, btnChangeDate2;

    private int year;
    private int month;
    private int day;

    static final int DATE_DIALOG_ID = 1;
    static final int DATE_DIALOG_ID2 = 2;
    int cur = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        tvDisplayDate = (TextView) findViewById(R.id.datestart);
        tvDisplayDate2 = (TextView) findViewById(R.id.dateend);
        final RelativeLayout timeframe = (RelativeLayout) findViewById(R.id.timeframe);

        final EditText ed = (EditText) findViewById(R.id.editText8);
        ed.addTextChangedListener(new com.example.luthf.pointofsale.Adapter.NumberTextWatcher(ed));

        final String start = ed.getText().toString();

        Log.d("star","star"+start);

        //setCurrentDateOnView();
        addListenerOnButton();

        TextView datestart = (TextView) findViewById(R.id.textView107);
        datestart.setText(new StringBuilder().append(month + 1)
                .append("-").append(day).append("-").append(year)
                .append(" "));


        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText ed = (EditText) findViewById(R.id.editText8);
                String start = ed.getText().toString();
                RelativeLayout description = (RelativeLayout) findViewById(R.id.drawercurrent);
                RelativeLayout desc = (RelativeLayout) findViewById(R.id.drawerdetil);

                description.setVisibility(View.GONE);
                desc.setVisibility(View.VISIBLE);

                TextView cash = (TextView) findViewById(R.id.textView109);
                cash.setText("Rp "+start);
            }
        });
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout description = (RelativeLayout) findViewById(R.id.drawercurrent);
                RelativeLayout desc = (RelativeLayout) findViewById(R.id.drawerdetil);

                description.setVisibility(View.VISIBLE);
                desc.setVisibility(View.GONE);
            }
        });

    }


    public void onRadioButtonClicked(View view) {
        RelativeLayout reportdetil = (RelativeLayout) findViewById(R.id.reportdetil);
        RelativeLayout timeframe = (RelativeLayout) findViewById(R.id.timeframe);
        RelativeLayout drawercurrent = (RelativeLayout) findViewById(R.id.drawercurrent);
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.sales:
                if (checked) {
                    timeframe.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.currentdrawer:
                if (checked) {
                    timeframe.setVisibility(View.GONE);
                    reportdetil.setVisibility(View.GONE);
                    drawercurrent.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.drawerhistory:
                if (checked) {
                    timeframe.setVisibility(View.GONE);
                }
                break;
        }
    }

    // display current date
    public void setCurrentDateOnView() {
        tvDisplayDate = (TextView) findViewById(R.id.datestart);
        tvDisplayDate2 = (TextView) findViewById(R.id.dateend);

        // set current date into textview
        tvDisplayDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(month + 1).append("-").append(day).append("-")
                .append(year).append(" "));
        tvDisplayDate2.setText(tvDisplayDate.getText().toString());
    }

    public void addListenerOnButton() {
        btnChangeDate = (Button) findViewById(R.id.button1);
        btnChangeDate.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
                btnChangeDate.setVisibility(View.GONE);
                btnChangeDate2.setVisibility(View.VISIBLE);
            }
        });
        btnChangeDate2 = (Button) findViewById(R.id.button2);
        btnChangeDate2.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID2);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                System.out.println("onCreateDialog  : " + id);
                cur = DATE_DIALOG_ID;
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month, day);
            case DATE_DIALOG_ID2:
                cur = DATE_DIALOG_ID2;
                System.out.println("onCreateDialog2  : " + id);
                // set date picker as current date
                return new DatePickerDialog(this, datePickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            if(cur == DATE_DIALOG_ID){
                // set selected date into textview
                tvDisplayDate.setText("Start Date " + new StringBuilder().append(month + 1)
                        .append("-").append(day).append("-").append(year)
                        .append(" "));
            }
            else{
                tvDisplayDate2.setText("Date End " + new StringBuilder().append(month + 1)
                        .append("-").append(day).append("-").append(year)
                        .append(" "));

                RelativeLayout timeframe = (RelativeLayout) findViewById(R.id.timeframe);
                timeframe.setVisibility(View.GONE);

                RelativeLayout reportdetil = (RelativeLayout) findViewById(R.id.reportdetil);
                reportdetil.setVisibility(View.VISIBLE);
            }
        }
    };








    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
