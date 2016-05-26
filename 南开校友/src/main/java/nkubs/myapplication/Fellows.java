package nkubs.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;



public class Fellows extends ActionBarActivity {

    private Button bt_newMsg;
    private Button bt_recMsg;
    private Button bt_senMsg;
    private Button bt_unrMsg;
    static int result;
    private DBUtil dbUtil;
    private int recSid;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 0) {
                bt_unrMsg.setText("未读信息"+ "  ("+msg.what +")");
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 我的校友");

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        recSid = preferences.getInt("_sid", 0);

        bt_unrMsg = (Button) findViewById(R.id.button_fellows_unrMsg);
        dbUtil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                        result = dbUtil.unreadMsg(recSid);
                        Log.i("信息", "" + result);
                        mhandler.obtainMessage(result).sendToTarget();

                }catch (Exception h){
                    mhandler.obtainMessage(0).sendToTarget();}
            }
        }).start();
        bt_unrMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Fellows.this, Fellows_unrMsg.class);
                startActivity(intent);
            }
        });


        bt_newMsg = (Button) findViewById(R.id.button_fellows_newMsg);
        bt_newMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Fellows.this, Fellows_FriendList.class);
                startActivity(intent);
            }
        });

        bt_recMsg = (Button) findViewById(R.id.button_fellows_recMsg);
        bt_recMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Fellows.this, Fellows_RecMsg.class);
                startActivity(intent);
            }
        });

        bt_senMsg = (Button) findViewById(R.id.button_fellows_senMsg);
        bt_senMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Fellows.this, Fellows_SenMsg.class);
                startActivity(intent);
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_friendsList:
                /*Toast.makeText(this, "List", Toast.LENGTH_SHORT).show();*/
                Intent intent1 = new Intent();
                intent1.setClass(Fellows.this, Fellows_FriendList.class);
                startActivity(intent1);
                return true;
            case R.id.action_add:
                /*Toast.makeText(this, "Add", Toast.LENGTH_SHORT).show();*/
                Intent intent2 = new Intent();
                intent2.setClass(Fellows.this, Fellows_Find.class);
                startActivity(intent2);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
