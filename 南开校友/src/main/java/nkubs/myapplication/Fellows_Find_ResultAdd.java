package nkubs.myapplication;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;


public class Fellows_Find_ResultAdd extends Activity {

    private int targetSid;
    private int _sid;
    private DBUtil dbutil;
    static HashMap<String, String> result = new HashMap<String, String>() ;
    private Boolean result_insert;
    private EditText et_name;
    private EditText et_gender;
    private EditText et_school;
    private EditText et_major;
    private EditText et_graduation;
    private EditText et_city;
    private EditText et_career;


    /*name, gender, school,major,graduation,city,career*/

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if(message.what == 1) {
                Log.i("信息", "进入目标用户信息输出方法");
                HashMap<String,String> hashMap = (HashMap<String, String>) message.obj;
                Log.i("信息",hashMap.get("name"));
                et_name.setText(hashMap.get("name"));
                if(hashMap.get("gender").equals("0")){
                    et_gender.setText("女");
                }else{
                    et_gender.setText("男");
                }
                et_school.setText(hashMap.get("school"));
                et_major.setText(hashMap.get("major"));
                et_graduation.setText(hashMap.get("graduation"));
                et_city.setText(hashMap.get("city"));
                et_career.setText(hashMap.get("career"));
            }else if(message.what == 2){
                Toast.makeText(Fellows_Find_ResultAdd.this, "数据输出异常！", Toast.LENGTH_SHORT).show();
            } else if (message.what == 3) {
                Toast.makeText(Fellows_Find_ResultAdd.this, "成功发送好友请求", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Fellows_Find_ResultAdd.this, Fellows_Find.class);
                startActivity(intent);
            } else if (message.what == 4) {
                Toast.makeText(Fellows_Find_ResultAdd.this, "发送好友请求失败！", Toast.LENGTH_SHORT).show();
            } else if (message.what == 5){
                Toast.makeText(Fellows_Find_ResultAdd.this, "您不可以给自己发送添加好友请求！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Fellows_Find_ResultAdd.this, "未知错误！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find_resultadd);

        et_career = (EditText) findViewById(R.id.EditText_ResultList_career);
        et_gender = (EditText) findViewById(R.id.EditText_ResultList_gender);
        et_city = (EditText) findViewById(R.id.EditText_ResultList_city);
        et_graduation = (EditText) findViewById(R.id.EditText_ResultList_graduation);
        et_major = (EditText) findViewById(R.id.EditText_ResultList_major);
        et_name = (EditText) findViewById(R.id.EditText_ResultLis_name);
        et_school = (EditText) findViewById(R.id.EditText_ResultList_school);
        Button button = (Button) findViewById(R.id.Button_ResultList_add);

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        targetSid = preferences.getInt("targetSid", 0);
        _sid = preferences.getInt("_sid", 0);
        Log.i("接收", targetSid+"");

        //记得初始化，系统自己不报错，也是醉醉的！长点心吧
        dbutil = new DBUtil();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbutil.targetSidInfo(targetSid);
                    Log.i("子线程", "传递");
                    //加上.sendToTarget().啊啊啊啊啊
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Fellows_Find_ResultAdd.this);
                builder.setTitle("添加好友");
                builder.setMessage("发送好友请求？");
                builder.setPositiveButton("算了吧", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                    .setNegativeButton("发送呗", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (_sid != targetSid) {
                                    Log.i("子线程", "进入发送好友请求方法");
                                    result_insert = dbutil.createRelation(_sid, targetSid);
                                 /* System.out.println(result_insert);*/
                                    if (result_insert) {
                                        mhandler.obtainMessage(3).sendToTarget(); //插入记录成功
                                    } else {
                                        mhandler.obtainMessage(4).sendToTarget(); //插入记录失败
                                    }
                                }else{
                                    Log.i("子线程","_sid = targetSid");
                                    mhandler.obtainMessage(5).sendToTarget(); //用户试图给自己发送好友请求
                                }
                            }
                        }).start();
                    }
                });
                builder.setCancelable(true);
                Dialog dialog = builder.create();
                dialog.show();
            }
        });
    }


}
