package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.List;
import java.util.Map;


public class Fellows_Find_Condition_list extends Activity implements AdapterView.OnItemClickListener{

    private DBUtil dbUtil;
    private List<Map<String,Object>> result;
    private String school ;
    private String graduation ;
    private String city ;
    private String career ;
    private List<Map<String,Object>> datalist;
    private SimpleAdapter simpleAdapter;
    private ListView listView;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if (message.what == 1) {
                /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
                datalist = (List<Map<String,Object>>)message.obj;
                simpleAdapter = new SimpleAdapter(Fellows_Find_Condition_list.this,datalist,R.layout.friendslist,new String[]{"name","city","career"},new int[]{R.id.textView_FriendsList_name,R.id.textView_FriendsList_city,R.id.textView_FriendsList_career});
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(Fellows_Find_Condition_list.this);
            }else{
                Toast.makeText(Fellows_Find_Condition_list.this, "条件查找校友失败！", Toast.LENGTH_SHORT).show();
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
        actionBar.setTitle(" 条件查找");


        Intent intent = getIntent();
        school = intent.getStringExtra("school");
        graduation = intent.getStringExtra("graduation");
        city = intent.getStringExtra("city");
        career = intent.getStringExtra("career");

        listView = (ListView) findViewById(R.id.listView_friends);

        dbUtil = new DBUtil();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("子线程","进入子线程");
                        try{
                            result = dbUtil.findByCondition(school,graduation,city,career);
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
       /* System.out.println(datalist);
        System.out.println(datalist.get(i).get("career"));
        System.out.println(datalist.get(i).get("city"));
        System.out.println(datalist.get(i).get("name"));
        System.out.println(datalist.get(i).get("target_sid"));*/
        int targetSid = Integer.parseInt(datalist.get(i).get("_mid").toString().trim());
        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("targetSid",targetSid);
        editor.apply();
        Intent intent = new Intent();
        intent.setClass(Fellows_Find_Condition_list.this, Fellows_Find_ResultAdd.class);
        startActivity(intent);
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //对用户按home icon的处理，本例只需关闭activity，就可返回上一activity，即主activity。
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
