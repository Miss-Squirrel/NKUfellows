package nkubs.myapplication;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Handler;
import java.util.logging.LogRecord;


public class TabFragment_Sid extends Fragment {

    private AutoCompleteTextView acTextView;
    private Button button;
    private EditText et_sid;
    private EditText et_name;
    private EditText et_date;
    static Boolean result;

    private String[] res = {"金融学", "金融工程", "管理科学与工程", "管理信息系统"};
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private DBUtil dbUtil;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 2) {
                Log.i("信息", "验证成功");
                Intent intent = new Intent();
                intent.putExtra("sid", msg.what);
                intent.setClass(getActivity(), SignUpFollow.class);
                startActivity(intent);
            }else if (msg.what == 1){
                Toast.makeText(getActivity(), "信息不匹配！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "验证失败！", Toast.LENGTH_SHORT).show();
            }
            }
        };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.framgent_signupsid, container, false);
       /* Log.i("信息","进入Fragment_one");*/

        et_sid = (EditText) view.findViewById(R.id.EditText_SignUp1_Sid);
        et_name = (EditText) view.findViewById(R.id.EditText_SignUp1_RealName);
        dbUtil = new DBUtil();


         /*
         * 初始化控件
         * 需要适配器
         * 初始化数据源--以用于匹配文本框输入的内容
         * 将adapter与当前AutoCompleteTextView绑定
         */
        acTextView = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView_SignUp1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getActivity(), android.R.layout.simple_list_item_1, res);
        acTextView.setAdapter(adapter);


        button = (Button) view.findViewById(R.id.button_SignUp1_Valid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Toast.makeText(getActivity(), "验证！", Toast.LENGTH_SHORT).show();*/
                final String school = acTextView.getText().toString();
                final int _sid = Integer.parseInt(et_sid.getText().toString().trim());
                final String name = et_name.getText().toString();
                final String birthdate = et_date.getText().toString();
                //Log.i("信息", _sid + school + name + date);
                new Thread(
                        new Runnable() {
                    public void run() {
                        try{
                        result = dbUtil.signUpCheck_sid(_sid, name, school, birthdate);
                            Log.i("信息", "" + result);
                            if(result) {
                            mhandler.obtainMessage(_sid).sendToTarget();
                             } else{
                            mhandler.obtainMessage(1).sendToTarget();}
                        }catch (Exception f){
                            mhandler.obtainMessage(2).sendToTarget();}
                        }
                    }).start();
            }
        });


        et_date = (EditText) view.findViewById(R.id.editText_SignUp1_BirthDate);
        //获取日历的一个对象
        calendar = Calendar.getInstance();
        //获取年月日信息
        year = calendar.get(Calendar.YEAR);
        //从0开始记月,造成很多麻烦
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        et_date.setText(year + "-" + (month + 1) + "-" + day);
        et_date.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    //参数很多，注意填写
                    new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            et_date.setText(i + "-" + (i1 + 1) + "-" + i2);
                            year = i;
                            month = i1;
                            day = i2;
                        }
                    }, year, month, day).show();
                }
            }
        });
        et_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        et_date.setText(i + "-" + (i1 + 1) + "-" + i2);
                        year = i;
                        month = i1;
                        day = i2;
                    }
                }, year, month, day).show();
            }
        });

        return view;

    }
}






