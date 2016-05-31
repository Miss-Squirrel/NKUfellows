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
import android.widget.Button;


public class Fellows_Find extends ActionBarActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button bt_request;
    private DBUtil dbUtil;
    static int result;
    private int recSid;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 0) {
                bt_request.setText("未处理好友请求"+ "  ("+msg.what +")");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 我的校友");

        button1 = (Button) findViewById(R.id.button_find_accurate);
        button2 = (Button) findViewById(R.id.button_find_condition);
        button3 = (Button) findViewById(R.id.button_find_contacts);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(Fellows_Find.this, Fellows_Find_Accurate.class);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(Fellows_Find.this, Fellows_Find_Condition_getInfo.class);
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3.setClass(Fellows_Find.this, Fellows_Find_Contacts.class);
                startActivity(intent3);
            }
        });


        bt_request = (Button) findViewById(R.id.button_find_request);
        dbUtil = new DBUtil();
        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        recSid = preferences.getInt("_sid", 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    result = dbUtil.requestNum(recSid);
                    Log.i("信息", "" + result);
                    mhandler.obtainMessage(result).sendToTarget();

                }catch (Exception h){
                    mhandler.obtainMessage(0).sendToTarget();}
            }
        }).start();
        bt_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Fellows_Find.this, Fellows_Request.class);
                startActivity(intent);
            }
        });

    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //对用户按home icon的处理，本例只需关闭activity，就可返回上一activity，即主activity。
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
