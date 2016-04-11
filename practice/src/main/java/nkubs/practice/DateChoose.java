package nkubs.practice;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by Administrator on 2016-04-08.
 */

public class DateChoose extends Activity{

    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private String str;
    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.datepicker);

        editText = (EditText) findViewById(R.id.editText_date);
        //datePicker = (DatePicker) findViewById(R.id.datePicker);


        //获取日历的一个对象
        calendar = Calendar.getInstance();
        //获取年月日信息
        year = calendar.get(Calendar.YEAR);
        //从0开始记月,造成很多麻烦
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        editText.setText(year + "-" + (month+1) + "-" + day);

        //初始化datePicker
        /*datePicker.init(year, calendar.get(Calendar.MONTH), day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                textView.setText(i+"-"+(i1+1)+"-"+i2);
            }
        }); */


       editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
           @Override
           public void onFocusChange(View view, boolean b) {
               if (b) {
                   //参数很多，注意填写
                   new DatePickerDialog(DateChoose.this, new DatePickerDialog.OnDateSetListener() {
                       @Override
                       public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                           editText.setText(i + "-" + (i1 + 1) + "-" + i2);
                           year = i;
                           month = i1;
                           day = i2;
                       }
                   }, year, month, day).show();
               }
           }
       });

       editText.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               new DatePickerDialog(DateChoose.this, new DatePickerDialog.OnDateSetListener() {
                   @Override
                   public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                       editText.setText(i + "-" + (i1 + 1) + "-" + i2);
                       year = i;
                       month = i1;
                       day = i2;
                   }
               }, year, month, day).show();
           }
       });


    }



}
