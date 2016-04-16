package nkubs.practice;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;


public class P_SQLiteCV extends Activity {

    public static final String TABLENAME = "friendsList2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_include);

        SQLiteDatabase db = openOrCreateDatabase("friendsList.db", MODE_PRIVATE, null);
        db.execSQL("create l_table if not exists friendsList2(_id integer primary key autoincrement, sid1 integer not null, sid2 integer not null)");

        ContentValues contentValues = new ContentValues();
        contentValues.put("sid1",1211768);
        contentValues.put("sid2",1211978);
        db.insert(TABLENAME,null,contentValues);
        contentValues.clear();
        contentValues.put("sid1",1211345);
        contentValues.put("sid2",1211768);
        db.insert(TABLENAME, null, contentValues);
        contentValues.clear();
        contentValues.put("sid1",1211978);
        contentValues.put("sid2",1211345);
        db.insert(TABLENAME, null, contentValues);
        contentValues.clear();

        //将id=2的记录sid1改为1211979
        contentValues.put("sid1",1211979);
        db.update(TABLENAME, contentValues, "_id=?", new String[]{"2"});
        db.delete(TABLENAME,"sid1 = ?",new String[]{"1211978"}); //删除sid1 =121978的记录
        Cursor cursor = db.query(TABLENAME,null,"_id > ?",new String[]{"0"},null,null,"sid1");
        //逐条打印记录
        if (cursor != null){
            String[] columns = cursor.getColumnNames();
            while (cursor.moveToNext()){
                for (String columnNames: columns){
                    Log.i("数据",cursor.getString(cursor.getColumnIndex(columnNames)));
                }
            }
            cursor.close();
        }
        db.close();


    }
}
