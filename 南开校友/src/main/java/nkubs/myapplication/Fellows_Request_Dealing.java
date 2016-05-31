package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;


public class Fellows_Request_Dealing extends Activity {

    private int targetSid;
    private int _sid;
    private DBUtil dbutil;
    static HashMap<String, String> result = new HashMap<String, String>() ;
    private Boolean accept;
    private Boolean reject;
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
            et_name.setEnabled(false);
            et_gender.setEnabled(false);
            et_school.setEnabled(false);
            et_major.setEnabled(false);
            et_graduation.setEnabled(false);
            et_city.setEnabled(false);
            et_career.setEnabled(false);
        }else if(message.what == 2){
            Toast.makeText(Fellows_Request_Dealing.this,"获取资料失败",Toast.LENGTH_SHORT).show();
            }else if(message.what == 3){
                Toast.makeText(Fellows_Request_Dealing.this,"已拒绝该好友请求",Toast.LENGTH_SHORT).show();
            }else if(message.what == 4){
                Toast.makeText(Fellows_Request_Dealing.this,"拒绝该好友请求失败",Toast.LENGTH_SHORT).show();
            }else if(message.what == 5){
                Toast.makeText(Fellows_Request_Dealing.this,"成功添加该好友",Toast.LENGTH_SHORT).show();
            }else if(message.what == 6){
                Toast.makeText(Fellows_Request_Dealing.this,"接受好友请求失败",Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find_resultadd);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 好友请求");

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

                AlertDialog.Builder builder = new AlertDialog.Builder(Fellows_Request_Dealing.this);
                builder.setTitle("添加好友");
                builder.setMessage("确认添加该好友？");
                builder.setPositiveButton("并不想加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                reject = dbutil.rejectRelation(targetSid,_sid);
                                if (reject) {
                                    mhandler.obtainMessage(3).sendToTarget();

                                }else {
                                    mhandler.obtainMessage(4).sendToTarget();
                                }
                            }
                        }).start();
                    }
                })
                        .setNegativeButton("确认添加", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                            accept = dbutil.acceptRelation(targetSid,_sid);
                                            if (accept) {
                                                mhandler.obtainMessage(5).sendToTarget();
                                            }else{
                                                mhandler.obtainMessage(6).sendToTarget();
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
