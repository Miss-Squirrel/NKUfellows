package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends Activity {

    private CheckBox checkBox;
    static Boolean result;
    private DBUtil dbUtil;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 2) {
                SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                if(checkBox.isChecked()) {
                    Log.i("信息", "登录成功，用户名已保存！");
                    editor.putBoolean("save", true);
                    editor.apply();
                }else{
                    Log.i("信息", "登录成功，不保存用户名！");
                    editor.putBoolean("save", false);
                    editor.apply();
                }
                editor.putInt("_sid",msg.what);
                editor.apply();
                Intent intent = new Intent();
                intent.setClass(Login.this, News.class);
                startActivity(intent);
            }else if (msg.what == 0){
                Toast.makeText(Login.this, "账号或密码错误！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Login.this, "登录异常！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将java与layout文件进行关联，即将布局xml文件引入带activity当中
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false); //enable 返回<
        actionBar.setDisplayShowHomeEnabled(false); //移除icon


        /*
         * 初始化当前所需要的控件
         * findViewById--返回的是一个View的对象（所有控件的父类）
         * 设置button的监听，通过监听器实现我们点击BUTTON要操作的事情
         */
        Button loginButton = (Button) findViewById(R.id.button_login);
        final Button cancelButton = (Button) findViewById(R.id.button_cancel);
        final EditText sidEditText = (EditText) findViewById(R.id.EditText_Login_sid);
        final EditText passEditText = (EditText) findViewById(R.id.EditText_password);
        final TextView textView = (TextView) findViewById(R.id.textView_gosignup);
        dbUtil = new DBUtil();

        SharedPreferences preferences = this.getSharedPreferences("Info",MODE_PRIVATE);
        int id = preferences.getInt("_sid",0);
        boolean bl = preferences.getBoolean("save",false);
        checkBox = (CheckBox) findViewById(R.id.checkBox_login_whethersave);
        EditText editText_sid = (EditText) findViewById(R.id.EditText_Login_sid);
        Log.i("学号", "" + id);
        if(bl){
            checkBox.setChecked(true);
            editText_sid.setText(id+"");
        }else{
            checkBox.setChecked(false);
        }


        /*匿名内部类*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int _sid = Integer.parseInt(sidEditText.getText().toString());
                final String password = passEditText.getText().toString();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            result = dbUtil.logInCheck(_sid, password);
                            Log.i("信息", "" + result);
                            if(result){
                                mhandler.obtainMessage(_sid).sendToTarget();
                            }else{
                                mhandler.obtainMessage(0).sendToTarget();
                            }
                        }catch (Exception g){
                            mhandler.obtainMessage(2).sendToTarget();
                        }
                    }
                }).start();
                //在当前onclick方法中监听点击button的动作
                /*System.out.println("我被点了耶");*/
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidEditText.setText("");
                passEditText.setText("");
            }
        });


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                intent.setClass(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

    }
}







        /* 外部类的实现 */
        /*loginButton.setOnClickListener(new MyOnClickListener() {
            @Override
            public void onClick(View v) {

                //调用父类的onClick方法
                super.onClick(v);

                Toast.makeText(Login.this, "button_login要执行的逻辑", Toast.LENGTH_LONG).show();
            }
        });

        cancelButton.setOnClickListener(new MyOnClickListener() {
            @Override
            public void onClick(View v) {
                super.onClick(v);
                Toast.makeText(Login.this, "button_cancel要执行的逻辑", Toast.LENGTH_LONG).show();
            }
        }); */


        /* 接口方式实现 */
        /*loginButton.setOnClickListener(this);
         */



    /*@Override
    public void onClick(View v) {
        Log.i("tag", "接口方式实现");
    }
    */




/*class MyOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        //让所有使用当前外部类的点击事件按钮都做出同一个动作，即改变button的透明度
        //Log.i("tag", "父类的onClick事件");
        v.setAlpha(0.5f);
    }

} */






