package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;
import java.util.HashMap;


public class Fellows_SenMsg_Detail extends Activity {

    private EditText recSid;
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
                recSid.setText(hashMap.get("name")+"("+hashMap.get("recSid")+")");
                theme.setText(hashMap.get("theme"));
                content.setText(hashMap.get("cont"));
                date.setText(hashMap.get("date"));
                recSid.setEnabled(false);
                theme.setEnabled(false);
                content.setEnabled(false);
                date.setEnabled(false);
            }else{
                Toast.makeText(Fellows_SenMsg_Detail.this, "获取发件信息失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_newmsg);

        Intent intent = getIntent();
        _mid = intent.getIntExtra("_mid", 0);

        recSid = (EditText) findViewById(R.id.EditText_NewMsg_TargSid);
        theme = (EditText) findViewById(R.id.EditText_NewMsg_Theme);
        content = (EditText) findViewById(R.id.EditText_NewMsg_Content);
        date = (EditText) findViewById(R.id.EditText_NewMsg_Date);

        dbUtil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbUtil.senMsgDetail(_mid);
                    Log.i("子线程", "传递");
                    System.out.println(result);
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();

    }
}
