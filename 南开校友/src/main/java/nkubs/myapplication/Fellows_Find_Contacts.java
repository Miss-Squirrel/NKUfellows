package nkubs.myapplication;

import android.app.ActionBar;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Fellows_Find_Contacts extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private List<String> list = new ArrayList<String>();
    static int _sid;
    private List<Map<String,Object>> result;
    private DBUtil dbUtil;
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
                simpleAdapter = new SimpleAdapter(Fellows_Find_Contacts.this,datalist,R.layout.friendslist,new String[]{"name","city","career"},new int[]{R.id.textView_FriendsList_name,R.id.textView_FriendsList_city,R.id.textView_FriendsList_career});
                listView.setAdapter(simpleAdapter);
                listView.setOnItemClickListener(Fellows_Find_Contacts.this);
            }else{
                Toast.makeText(Fellows_Find_Contacts.this, "通讯录匹配失败！", Toast.LENGTH_SHORT).show();
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
        actionBar.setTitle(" 查找校友");

        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        _sid = preferences.getInt("_sid", 0);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("联系人", id + "   " + name);
                Cursor c1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                if (c1 != null) {
                    int i = 0;
                    while (c1.moveToNext()) {
                        String mobile = c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        list.add(i, mobile);
                        i++;
                    }
                }
            }
        }


        dbUtil = new DBUtil();
        listView = (ListView) findViewById(R.id.listView_friends);
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbUtil.contactMatch(_sid,list);
                    Log.i("子线程", "传递");
                    //加上.sendToTarget().啊啊啊啊啊
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
        int targetSid = Integer.parseInt(datalist.get(i).get("target_sid").toString().trim());
        SharedPreferences preferences = getSharedPreferences("Info",MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("targetSid",targetSid);
        editor.apply();
        Intent intent = new Intent();
        intent.setClass(Fellows_Find_Contacts.this, Fellows_Find_ResultAdd.class);
        startActivity(intent);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: //对用户按home icon的处理，本例只需关闭activity，就可返回上一activity，即主activity。
                System.out.println("Home is press");
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
