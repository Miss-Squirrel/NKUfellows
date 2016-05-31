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
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;


public class Fellows_NewMsg extends ActionBarActivity {

    private EditText et_targSid;
    private EditText et_theme;
    private EditText et_content;
    private EditText et_date;
    private int senSid;
    private int recSid;
    private String name;
    private String theme;
    private String cont;
    private DBUtil dbUtil;
    private boolean result;
    private Calendar calendar;
    private int year;
    private int month;
    private int day;
    private String date;



    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 2) {
                Toast.makeText(Fellows_NewMsg.this,"信息发送成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setClass(Fellows_NewMsg.this, Fellows.class);
                startActivity(intent);
            }else if (msg.what == 0){
                Toast.makeText(Fellows_NewMsg.this, "发送信息失败！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(Fellows_NewMsg.this, "未知异常！", Toast.LENGTH_SHORT).show();
            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_newmsg);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 我的校友");

        final Intent intent = getIntent();
        recSid = intent.getIntExtra("recSid", 0);
        name = intent.getStringExtra("name");

        SharedPreferences preferences = this.getSharedPreferences("Info",MODE_PRIVATE);
        senSid = preferences.getInt("_sid",0);


        et_targSid = (EditText) findViewById(R.id.EditText_NewMsg_TargSid);
        et_theme = (EditText) findViewById(R.id.EditText_NewMsg_Theme);
        et_content = (EditText) findViewById(R.id.EditText_NewMsg_Content);
        et_date = (EditText) findViewById(R.id.EditText_NewMsg_Date);

        et_targSid.setText(name+"（" + recSid + "）");
        calendar = Calendar.getInstance();
        //获取年月日信息
        year = calendar.get(Calendar.YEAR);
        //从0开始记月,造成很多麻烦
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        date = year + "-" + (month + 1) + "-" + day;
        et_date.setText(date);

        dbUtil = new DBUtil();

    }



    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newmsg, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                theme = et_theme.getText().toString();
                cont = et_content.getText().toString();
                System.out.println("信息内容"+theme + cont);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            result = dbUtil.sendMsg(senSid, recSid,theme,cont,date);
                            Log.i("信息", "" + result);
                            if(result){
                                mhandler.obtainMessage(senSid).sendToTarget();
                            }else{
                                mhandler.obtainMessage(0).sendToTarget();
                            }
                        }catch (Exception g){
                            mhandler.obtainMessage(2).sendToTarget();
                        }
                    }
                }).start();
                return true;
        }
    }




}
