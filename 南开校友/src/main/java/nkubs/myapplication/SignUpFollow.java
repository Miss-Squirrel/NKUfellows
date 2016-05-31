package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SignUpFollow extends Activity implements RadioGroup.OnCheckedChangeListener,AdapterView.OnItemSelectedListener{

    private RadioGroup rg;
    private Button su;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private List<String> list;

    private int pub = 1 ;
    private int gender = 1;
    private String career;
    private DBUtil dbutil;
    static Boolean result;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if (msg.what > 2) {
                SharedPreferences preferences = getSharedPreferences("Info", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("_sid", msg.what);
                editor.putBoolean("save", false);
                editor.apply(); //一定得commit，否则不生效！

                AlertDialog.Builder builder = new AlertDialog.Builder(SignUpFollow.this);
                builder.setTitle("注册成功");
                builder.setMessage("您的学号为"+msg.what+",请牢记！");
                builder.setPositiveButton("记住了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("信息", "注册成功");
                        Intent intent = new Intent();
                        intent.setClass(SignUpFollow.this, Login.class);
                        startActivity(intent);
                    }
                });
                builder.setCancelable(true);
                Dialog dialog = builder.create();
                dialog.show();

            }else if (msg.what == 1){
                Toast.makeText(SignUpFollow.this, "注册失败！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(SignUpFollow.this, "注册异常！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupfollow);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //移除icon

        Intent intent = getIntent();
        //0为缺省值
        final int _sid = intent.getIntExtra("sid",0);
        Log.i("接收", _sid+"");


        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("tag", isChecked + "");
                if(isChecked){
                    pub = 1;
                }else{
                    pub = 0;
                }
                if (isChecked) {
                    Toast.makeText(SignUpFollow.this, "公开手机号", Toast.LENGTH_SHORT).show();
                    //获得checkbox的文本内容
                    String text = checkBox.getText().toString();
                    Log.i("tag", text);
                } else
                    Toast.makeText(SignUpFollow.this, "不公开手机号", Toast.LENGTH_SHORT).show();
            }
        });


        rg = (RadioGroup) findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(this);

        final EditText editText_city = (EditText) findViewById(R.id.EditText_SignUpFollow_City);
        final EditText editText_mobile = (EditText) findViewById(R.id.EditText_SignUpFollow_MobileNum);
        final EditText editText_password = (EditText) findViewById(R.id.EditText_SignUpFollow_Password);
        dbutil = new DBUtil();


        spinner = (Spinner) findViewById(R.id.Spinner_SignUpFollow_Career);
        //准备数据源
        list = new ArrayList<String>();
        list.add("金融");
        list.add("信息产业");
        list.add("工商管理");
        list.add("其他");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        //设置一个下拉列表样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //绑监听器！
        spinner.setOnItemSelectedListener(this);




        su = (Button) findViewById(R.id.button_signUp);
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String city = editText_city.getText().toString();
                final String mobile = editText_mobile.getText().toString();
                final String password = editText_password.getText().toString();
                Log.i("信息",_sid+"+"+gender+"+"+city+"+"+career+"+"+mobile+"+"+password+"+"+pub);
                new Thread(
                        new Runnable() {
                            public void run() {
                                try{
                                    result = dbutil.insertUserInfo(_sid, gender, city, career,mobile,password,pub);
                                    Log.i("信息", "" + result);
                                    if(result) {
                                        mhandler.obtainMessage(_sid).sendToTarget();
                                    } else{
                                        mhandler.obtainMessage(1).sendToTarget();}
                                }catch (Exception g){
                                    mhandler.obtainMessage(2).sendToTarget();}
                            }
                        }).start();
            }
        });



    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //  checkedId为被选中状态RadioButton的id
        switch (checkedId) {
            case R.id.radioButton1:
                gender = 1;
                Log.i("tag", "男");
                break;
            default:
                gender = 0;
                Log.i("tag", "女");
                break;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*textView.setText( list.get(i) +"");*/
        career = list.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {}



}
