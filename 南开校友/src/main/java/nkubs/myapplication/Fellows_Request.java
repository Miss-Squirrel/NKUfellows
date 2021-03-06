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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.List;
import java.util.Map;


public class Fellows_Request extends ActionBarActivity implements AdapterView.OnItemClickListener{
    private DBUtil dbUtil;
    private List<Map<String,Object>> result;
    private List<Map<String,Object>> datalist;
    private SimpleAdapter simpleAdapter;
    private ListView listView;
    private int _sid;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if (message.what == 1) {
                /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
                datalist = (List<Map<String,Object>>)message.obj;
                simpleAdapter = new SimpleAdapter(Fellows_Request.this,datalist,R.layout.friendslist,new String[]{"name","city","career"},new int[]{R.id.textView_FriendsList_name,R.id.textView_FriendsList_city,R.id.textView_FriendsList_career});
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(Fellows_Request.this);
            }else{
                Toast.makeText(Fellows_Request.this, "无法加载好友请求列表！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_friends);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 我的校友");


        listView = (ListView) findViewById(R.id.listView_friends);

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid", 0);

        dbUtil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbUtil.requestList(_sid);
                    Log.i("子线程", "传递");
                    System.out.println(result);
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();
    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        int targetSid = Integer.parseInt(datalist.get(i).get("_sid1").toString().trim());
        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("targetSid",targetSid);
        editor.apply();
        Intent intent = new Intent();
        intent.setClass(Fellows_Request.this, Fellows_Request_Dealing.class);
        startActivity(intent);
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
