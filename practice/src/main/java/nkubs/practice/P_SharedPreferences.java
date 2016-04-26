package nkubs.practice;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;


public class P_SharedPreferences extends Activity {

    private EditText etSid;
    private EditText etPass;
    private CheckBox checkBox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_sharedpreferences);


        /*//SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences preferences = getSharedPreferences("myPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("name", "张三");
        editor.putInt("学号", 1211978);
        editor.putString("密码", "123456");
        editor.apply(); //一定得commit，否则不生效！
        System.out.println(preferences.getString("name","名字"));
        System.out.println(preferences.getInt("学号",0));
        */


        etSid = (EditText) findViewById(R.id.editText_sid);
        etPass = (EditText) findViewById(R.id.editText_password);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        preferences = getSharedPreferences("myPref",MODE_PRIVATE);
        editor = preferences.edit();



        int id = preferences.getInt("_sid",0);
        boolean bl = preferences.getBoolean("save",false);
        Log.i("学号", "" + id);
        if(bl){
            checkBox.setChecked(true);
            etSid.setText(id+"");
        }else{
            checkBox.setChecked(false);
        }

    }

    public void doClick(View v){
        switch (v.getId()){
            case R.id.button_login:
            {
                int sid = Integer.parseInt(etSid.getText().toString().trim()); //去掉首尾空格
                String pass = etPass.getText().toString().trim();
                if(sid == preferences.getInt("学号",0) && pass.equals(preferences.getString("密码","")))
                {
                    if(checkBox.isChecked()){
                        editor.putBoolean("是否显示用户名",true);
                        editor.commit();
                        Toast.makeText(P_SharedPreferences.this, "登录成功，用户名已保存", Toast.LENGTH_SHORT).show();
                    }else{
                        editor.putBoolean("是否显示用户名",false);
                        Toast.makeText(P_SharedPreferences.this, "登陆成功，用户名已移除", Toast.LENGTH_SHORT).show();
                        editor.commit();
                    }
                }else{
                    Toast.makeText(P_SharedPreferences.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.button_cancel:
            {
                int id = preferences.getInt("学号",0);
                if(id == 0){
                    checkBox.setChecked(false);
                }else{
                    checkBox.setChecked(true);
                    Log.i("学号", "doClick000");
                    etSid.setText(id+"");
                }
                etPass.setText("");
                break;
            }
        }

    }
}
