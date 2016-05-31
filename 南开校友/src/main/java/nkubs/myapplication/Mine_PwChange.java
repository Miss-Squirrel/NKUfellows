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


public class Mine_PwChange extends ActionBarActivity {

    private EditText et_pw;
    private Button button;
    private String pw;
    private DBUtil dbUtil;
    private int _sid;
    private Boolean result;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if (msg.what > 2) {
                Toast.makeText(Mine_PwChange.this, "密码验证通过！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Mine_PwChange.this, Mine_PwChange_Submit.class);
                startActivity(intent);
            }else if(msg.what == 0){
                Toast.makeText(Mine_PwChange.this, "密码错误，请重新输入！", Toast.LENGTH_SHORT).show();
                et_pw.setText("");
                et_pw.setFocusable(true);
            }else{
                Toast.makeText(Mine_PwChange.this, "未知异常！", Toast.LENGTH_SHORT).show();

            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_pwchange);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 账号管理");

        et_pw = (EditText) findViewById(R.id.EditText_pwChange_Password);
        button = (Button) findViewById(R.id.button_pwChange_submit);

        SharedPreferences preferences = this.getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid",0);

        dbUtil = new DBUtil();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pw = et_pw.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            result = dbUtil.logInCheck(_sid, pw);
                            Log.i("信息", "" + result);
                            if(result){
                                mhandler.obtainMessage(_sid).sendToTarget();
                            }else{
                                mhandler.obtainMessage(0).sendToTarget();
                            }
                        }catch (Exception g){
                            mhandler.obtainMessage(1).sendToTarget();
                        }
                    }
                }).start();
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
