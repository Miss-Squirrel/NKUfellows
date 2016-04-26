package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;


public class Fellows_FriendList_DetailInfo extends Activity {


    private int friendSid;
    private DBUtil dbutil;
    static HashMap<String, String> result = new HashMap<String, String>() ;
    private EditText et_name;
    private EditText et_gender;
    private EditText et_school;
    private EditText et_major;
    private EditText et_graduation;
    private EditText et_city;
    private EditText et_career;
    private EditText et_mobile;


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
                et_mobile.setText(hashMap.get("mobile"));
            }else if(message.what == 2){
                Toast.makeText(Fellows_FriendList_DetailInfo.this, "数据输出异常！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Fellows_FriendList_DetailInfo.this, "未知错误！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_friendlist_detailinfo);

        et_career = (EditText) findViewById(R.id.EditText_DetailInfo_career);
        et_gender = (EditText) findViewById(R.id.EditText_DetailInfo_gender);
        et_city = (EditText) findViewById(R.id.EditText_DetailInfo_city);
        et_graduation = (EditText) findViewById(R.id.EditText_DetailInfo_graduation);
        et_major = (EditText) findViewById(R.id.EditText_DetailInfo_major);
        et_name = (EditText) findViewById(R.id.EditText_DetailInfo_name);
        et_school = (EditText) findViewById(R.id.EditText_DetailInfo_school);
        et_mobile = (EditText) findViewById(R.id.EditText_DetailInfo_mobile);


        Intent intent = getIntent();
        friendSid = intent.getIntExtra("friendSid",0);
        Log.i("接收", friendSid+"");


        //记得初始化，系统自己不报错，也是醉醉的！长点心吧
        dbutil = new DBUtil();


        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbutil.targetSidInfo(friendSid);
                    Log.i("子线程", "传递");
                    //加上.sendToTarget().啊啊啊啊啊
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();



    }

}
