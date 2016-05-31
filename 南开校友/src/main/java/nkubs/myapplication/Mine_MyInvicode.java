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
import android.widget.EditText;
import android.widget.Toast;


public class Mine_MyInvicode extends ActionBarActivity {

    private EditText editText;
    private Button button;
    private String result;
    private DBUtil dbutil;
    private int _sid;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if (message.what == 1) {
                String code = (String) message.obj;
                editText.setText(code);
            }else{
                Toast.makeText(Mine_MyInvicode.this, "获取邀请码失败", Toast.LENGTH_SHORT);
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_myinvicode);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 账号管理");

        editText = (EditText) findViewById(R.id.EditText_MyInvicode_code);
        button = (Button) findViewById(R.id.button_MyInvicode);

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid", 0);

        dbutil = new DBUtil();
        new Thread(new Runnable() {
            public void run() {
                try{
                    result = dbutil.myInvicode(_sid);
                    Log.i("信息", "" + result);
                        mhandler.obtainMessage(1,result).sendToTarget();
                }catch (Exception g){
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Mine_MyInvicode.this, Mine.class);
                startActivity(intent);
            }
        });

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
