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
import android.widget.RadioGroup;
import android.widget.Toast;


public class Fellows_Find_Accurate extends Activity {

    private RadioGroup radioGroup;
    private Button button;
    private DBUtil dbutil;
    static int result;
    private Boolean way;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 2) {
                Log.i("信息", "查询找到该用户");
                SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("targetSid", msg.what);
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(Fellows_Find_Accurate.this, Fellows_Find_ResultAdd.class);
                startActivity(intent);
            }else if (msg.what == 0){
                Toast.makeText(Fellows_Find_Accurate.this, "用户不存在或未公开手机号码！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Fellows_Find_Accurate.this, "查询异常！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find_accurate);

        radioGroup = (RadioGroup) findViewById(R.id.RadioGroup_Find_Accurate);
        final EditText editText = (EditText) findViewById(R.id.editText_find_accurate);
        dbutil = new DBUtil();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.RadioButton_Find_Accurate_mobile:
                        way = true;
                        Log.i("tag", "手机号查找");
                        break;
                    default:
                        way = false;
                        Log.i("tag", "姓名查找");
                        break;
                }
            }
        });

        button = (Button) findViewById(R.id.Button_Find_Accurate);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    String mobile = editText.getText().toString();
                    @Override
                    public void run() {
                        try {
                            if (way) {
                                result = dbutil.find_mobile(mobile);
                                Log.i("信息", "" + result);
                                mhandler.obtainMessage(result).sendToTarget();
                            }
                        }catch (Exception h){
                            mhandler.obtainMessage(2).sendToTarget();}
                        }
                    }).start();
            }
        });

    }
}



/*new Thread(
        new Runnable() {
public void run() {
        try{
        result = dbutil.find_mobile(String mobile);
        Log.i("信息", "" + result);
        if(result) {
        mhandler.obtainMessage(_sid).sendToTarget();
        } else{
        mhandler.obtainMessage(1).sendToTarget();}
        }catch (Exception g){
        mhandler.obtainMessage(2).sendToTarget();}
        }
        }).start();*/
