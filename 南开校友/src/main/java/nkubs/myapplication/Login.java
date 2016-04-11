package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //将java与layout文件进行关联，即将布局xml文件引入带activity当中
        setContentView(R.layout.activity_login);


        /*
         * 初始化当前所需要的控件
         * findViewById--返回的是一个View的对象（所有控件的父类）
         * 设置button的监听，通过监听器实现我们点击BUTTON要操作的事情
         */
        Button loginButton = (Button) findViewById(R.id.button_login);
        Button searchButton = (Button) findViewById(R.id.button_search);


        /*匿名内部类*/
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //在当前onclick方法中监听点击button的动作
                System.out.println("我被点了耶");
            }
        });


        editText = (EditText) findViewById(R.id.EditText_sid);




        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //从getText方法提取的内容为Editable型，用toString转化为String类型，再用Integer.parseInt转为整型
                int sid = Integer.parseInt(editText.getText().toString());
                Log.i("传递", sid +"");
                Intent intent = new Intent(Login.this, Search.class);
                intent.putExtra("sid", sid);
                startActivity(intent);
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






