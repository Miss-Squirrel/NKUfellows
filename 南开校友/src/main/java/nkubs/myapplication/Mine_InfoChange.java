package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Mine_InfoChange extends ActionBarActivity implements AdapterView.OnItemSelectedListener {

    private EditText et_city;
    private Spinner spinner;
    private EditText et_mobile;
    private CheckBox checkBox;
    private Button bt_modify;
    private int _sid;
    private DBUtil dbutil;
    private HashMap<String, String> result;
    private Boolean resultMo;
    private ArrayAdapter arrayAdapter;
    private List<String> list;
    private String career;
    private String city;
    private String mobile;
    private int pub;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if(message.what == 1) {
                Log.i("信息", "进入当前用户信息输出方法");
                HashMap<String,String> hashMap = (HashMap<String, String>) message.obj;
                Log.i("信息",hashMap.get("city"));
                career = hashMap.get("career").trim();
                pub = Integer.parseInt(hashMap.get("pub"));

                et_city.setText(hashMap.get("city"));
                final int position = arrayAdapter.getPosition(career);
                System.out.println(career);
                System.out.println(position);
                spinner.setSelection(position);
                et_mobile.setText(hashMap.get("mobile"));
                if(pub == 0){
                    checkBox.setChecked(false);
                }else{
                    checkBox.setChecked(true);
                }

            }else if(message.what == 2){
                Toast.makeText(Mine_InfoChange.this, "获取信息失败", Toast.LENGTH_SHORT).show();
            }else if(message.what == 3){
                Toast.makeText(Mine_InfoChange.this, "修改成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Mine_InfoChange.this, Mine.class);
                startActivity(intent);
            }else if(message.what == 4){
                Toast.makeText(Mine_InfoChange.this, "修改失败", Toast.LENGTH_SHORT).show();
            }else if(message.what == 5){
                Toast.makeText(Mine_InfoChange.this, "未知异常", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Mine_InfoChange.this, Mine.class);
                startActivity(intent);
            }


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_infochange);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 账号管理");


        et_city = (EditText) findViewById(R.id.EditText_Mine_City);
        spinner = (Spinner) findViewById(R.id.Spinner_Mine_Career);
        et_mobile = (EditText) findViewById(R.id.EditText_Mine_MobileNum);
        checkBox = (CheckBox) findViewById(R.id.checkBox_Mine);
        bt_modify = (Button) findViewById(R.id.button_Mine_modify);

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid", 0);

        list = new ArrayList<String>();
        list.add("金融");
        list.add("信息产业");
        list.add("工商管理");
        list.add("能源开采");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        //设置一个下拉列表样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        dbutil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbutil._SidInfo(_sid);
                    Log.i("子线程", "传递");
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("tag", isChecked + "");
                if(isChecked){
                    pub = 1;
                }else{
                    pub = 0;
                }
            }
        });


        bt_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                city = et_city.getText().toString().trim();
                mobile = et_mobile.getText().toString().trim();
                System.out.println(_sid+city+career+mobile+pub);
                new Thread(new Runnable() {
                            public void run() {
                                try{
                                    resultMo = dbutil.modifyUserInfo(_sid,city, career,mobile,pub);
                                    Log.i("信息", "" + resultMo);
                                    if(resultMo) {
                                        mhandler.obtainMessage(3).sendToTarget();
                                    } else{
                                        mhandler.obtainMessage(4).sendToTarget();}
                                }catch (Exception g){
                                    mhandler.obtainMessage(5).sendToTarget();}
                            }
                        }).start();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        career = list.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
