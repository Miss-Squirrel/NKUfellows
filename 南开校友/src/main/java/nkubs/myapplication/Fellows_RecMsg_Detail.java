package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Fellows_RecMsg_Detail extends ActionBarActivity {

    private EditText senSid;
    private EditText theme;
    private EditText content;
    private EditText date;
    private DBUtil dbUtil;
    private HashMap<String,Object> result;
    private int _mid;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if (message.what == 1) {
                /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
                HashMap<String,String> hashMap = (HashMap<String, String>) message.obj;
                senSid.setText(hashMap.get("name")+"("+hashMap.get("senSid")+")");
                theme.setText(hashMap.get("theme"));
                content.setText(hashMap.get("cont"));
                date.setText(hashMap.get("date"));
                senSid.setEnabled(false);
                theme.setEnabled(false);
                content.setEnabled(false);
                date.setEnabled(false);
            }else{
                Toast.makeText(Fellows_RecMsg_Detail.this, "获取收件信息失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_recmsg_detail);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 收件箱");

        Intent intent = getIntent();
        _mid = intent.getIntExtra("_mid", 0);

        senSid = (EditText) findViewById(R.id.EditText_RecMsg_Detail_senSid);
        theme = (EditText) findViewById(R.id.EditText_RecMsg_Detail_theme);
        content = (EditText) findViewById(R.id.EditText_RecMsg_Detail_content);
        date = (EditText) findViewById(R.id.EditText_RecMsg_Detail_date);

        dbUtil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbUtil.recMsgDetail(_mid);
                    Log.i("子线程", "传递");
                    System.out.println(result);
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();

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
