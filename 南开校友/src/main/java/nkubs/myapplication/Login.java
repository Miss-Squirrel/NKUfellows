package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

    private EditText sidEditText;
    private EditText passEditText;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将java与layout文件进行关联，即将布局xml文件引入带activity当中
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setDisplayShowHomeEnabled(false); //移除icon


        /*
         * 初始化当前所需要的控件
         * findViewById--返回的是一个View的对象（所有控件的父类）
         * 设置button的监听，通过监听器实现我们点击BUTTON要操作的事情
         */
        Button loginButton = (Button) findViewById(R.id.button_login);
        final Button cancelButton = (Button) findViewById(R.id.button_cancel);
        sidEditText = (EditText) findViewById(R.id.EditText_sid);
        passEditText = (EditText) findViewById(R.id.EditText_password);
        textView = (TextView) findViewById(R.id.textView_gosignup);


        /*匿名内部类*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在当前onclick方法中监听点击button的动作
                /*System.out.println("我被点了耶");*/
                Intent intent = getIntent();
                intent.setClass(Login.this, News.class);
                startActivity(intent);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sidEditText.setText("");
                passEditText.setText("");
            }
        });



     /* searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //从getText方法提取的内容为Editable型，用toString转化为String类型，再用Integer.parseInt转为整型
                int sid = Integer.parseInt(editText.getText().toString());
                Log.i("传递", sid +"");
                Intent intent = new Intent(Login.this, Search.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
            }
        });*/

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Login.this, SignUp.class);
                startActivity(intent2);
            }
        });


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


    }

    /*@Override
    public void onClick(View v) {
        Log.i("tag", "接口方式实现");
    }
    */


}


/*class MyOnClickListener implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        //让所有使用当前外部类的点击事件按钮都做出同一个动作，即改变button的透明度
        //Log.i("tag", "父类的onClick事件");
        v.setAlpha(0.5f);
    }

} */






