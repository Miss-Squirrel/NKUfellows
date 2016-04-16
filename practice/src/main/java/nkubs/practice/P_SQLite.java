package nkubs.practice;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;


public class P_SQLite extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_include);
        //每个程序都有自己的数据库，默认情况下是各自互不干扰
        //创建一个数据库，并打开，如果已经存在，就直接打开
        SQLiteDatabase db = openOrCreateDatabase("UserInfo.db",MODE_PRIVATE,null);
        //创建表,主键名称前加下划线_！
        db.execSQL("create l_table if not exists usertb(_sid integer primary key,name text not null,city text not null)");
        //插入数据，注意字段用单引号！
        //db.execSQL("insert into usertb(_sid,name,city) values(1211978,'张三','天津')");
        //db.execSQL("insert into usertb(_sid,name,city) values(1211768,'奋斗','北京')");
        //db.execSQL("insert into usertb(_sid,name,city) values(1211345,'小明','上海')");

        Cursor cursor = db.rawQuery("select * from usertb", null);
        if (cursor != null) {
            //判断能否移动到下一条
            while (cursor.moveToNext()){
                //cursor.getInt(第几列)
                System.out.println(cursor.getInt(cursor.getColumnIndex("_sid")));
                System.out.println(cursor.getString(cursor.getColumnIndex("name")));
                System.out.println(cursor.getString(cursor.getColumnIndex("city")));
            }
        }
        cursor.close();
        db.close();
    }
}
