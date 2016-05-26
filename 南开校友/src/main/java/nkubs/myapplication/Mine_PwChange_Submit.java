package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Mine_PwChange_Submit extends Activity {

    private EditText et_p1;
    private EditText et_p2;
    private Button button;
    private String p1;
    private String p2;
    private int _sid;
    private DBUtil dbUtil;
    private Boolean result;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if (msg.what > 2) {
                Toast.makeText(Mine_PwChange_Submit.this, "密码修改成功！", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Mine_PwChange_Submit.this, Mine.class);
                startActivity(intent);
            }else if(msg.what == 0){
                Toast.makeText(Mine_PwChange_Submit.this, "密码修改失败！", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(Mine_PwChange_Submit.this, "未知异常！", Toast.LENGTH_SHORT).show();

            }
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine_pwchange_submit);

        et_p1 = (EditText) findViewById(R.id.EditText_pwChange_newPassword);
        et_p2 = (EditText) findViewById(R.id.EditText_pwChange_PasswordRepeat);
        button = (Button) findViewById(R.id.button_pwChange_valid);

        SharedPreferences preferences = this.getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid",0);


        dbUtil = new DBUtil();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                p1 = et_p1.getText().toString();
                p2 = et_p2.getText().toString();
                if(p1.equals(p2)){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                result = dbUtil.modifyPassword(_sid, p1);
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
                }else{
                    Toast.makeText(Mine_PwChange_Submit.this, "两次输入的密码不相符！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
