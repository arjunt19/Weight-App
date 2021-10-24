package com.example.mark2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClickedActivity extends AppCompatActivity {

    private  TextView month;
    private  TextView day;
    private String test;
    private  TextView year;
    public static final String UPDATE_DATE = "date_updated";
    public static final String UPDATE_WEIGHT = "weight_updated";
    public static final String weight_id = "weight_id";

    public static final String UPDATE_DESCRIPTION = "description_updated";
    public static final String reply_id = "reply_id";

    private TextView weight;
    private TextView description;
    private final String[] months = {"January", "February","March","April","May","June","July","August","September","October","November","December"};
    private long date;
    public static final String WEIGHT = "com.example.android.mark2.WEIGHT";

    public static final String DESCRIPTION = "com.example.android.mark2.DESCRIPTION";
    public static final String LONG = "com.example.android.mark2.LONG";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clicked);

        month = findViewById(R.id.month_new);
        day = findViewById(R.id.day_new);
        year = findViewById(R.id.year_new);
        weight = findViewById(R.id.weight_new);
        description=findViewById(R.id.description_new);
        final Calendar c  = Calendar.getInstance();
        final int year_start = c.get(Calendar.YEAR);
        final int month_start = c.get(Calendar.MONTH);
        final int day_start = c.get(Calendar.DAY_OF_MONTH);
        processDate(year_start,month_start,day_start);

        final Bundle extras = getIntent().getExtras();
        if(extras!=null){
            weight.setText(extras.getInt(UPDATE_WEIGHT) + "");
            description.setText(extras.getString(UPDATE_DESCRIPTION));
            long dateOld = extras.getLong(UPDATE_DATE);
            String[] dateComps = longToDate(dateOld);
            this.year.setText(dateComps[0]);
            this.month.setText((months[Integer.parseInt(dateComps[1])-1]));
            this.day.setText(dateComps[2]);


        }

        Button select_date =  findViewById(R.id.date_selector);
        select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new choose_date();
                newFragment.show(getSupportFragmentManager(),"datePicker");

            }
        });

        Button finishTransaction = findViewById(R.id.finish_transaction);
        finishTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                if(TextUtils.isEmpty(weight.getText())){
                    setResult(RESULT_CANCELED,intent);
                }
                else{
                    if(TextUtils.isEmpty(description.getText())){
                        intent.putExtra(DESCRIPTION, "");
                    }
                    else{
                        intent.putExtra(DESCRIPTION, description.getText().toString());
                    }
                    intent.putExtra(WEIGHT, Integer.parseInt(weight.getText().toString()));
                    intent.putExtra(LONG, date);
                    if (extras != null && extras.containsKey(weight_id)) {
                        int id = extras.getInt(weight_id, -1);
                        if (id != -1) {
                            intent.putExtra(reply_id, id);
                        }
                    }
                    setResult(RESULT_OK, intent);

                }
                finish();
            }
        });

    }

    public void processDate(int year, int month, int day){
        Calendar c = Calendar.getInstance();
        c.set(year,month,day,0,0);
        Date date = c.getTime();
        long dateLong  = date.getTime()/1000;
        this.date = dateLong;
        String[] dateComps =  longToDate(dateLong);
        this.year.setText(dateComps[0]);
        this.month.setText((months[Integer.parseInt(dateComps[1])-1]));
        this.day.setText(dateComps[2]);

    }

    private String[] longToDate(long date){
        Date tempDate = new java.util.Date(date*1000L);
        SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(tempDate);
        return formattedDate.split("-");

    }
}