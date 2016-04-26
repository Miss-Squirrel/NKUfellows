package nkubs.practice;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;


public class P_ContentProvider extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,new String[]{ContactsContract.Contacts._ID, ContactsContract.Contacts.DISPLAY_NAME},null,null,null);
        if(cursor != null){
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.i("联系人", id +"   " +name);
                Cursor c1 = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.TYPE}, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + id, null, null);
                if(c1 != null){
                    while (c1.moveToNext()){
                        int type = c1.getInt(c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE));
                        if(type == ContactsContract.CommonDataKinds.Phone.TYPE_HOME){
                            Log.i("家庭电话",c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }else if(type == ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE){
                            Log.i("家庭电话",c1.getString(c1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                        }
                    }
                }
                c1.close();
                //根据ID查询联系人邮箱
               /* Cursor c2 = contentResolver.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, new String[]{ContactsContract.CommonDataKinds.Email.DATA, ContactsContract.CommonDataKinds.Email.TYPE}, ContactsContract.CommonDataKinds.Email.CONTACT_ID + "=" + id, null, null);
               if (c2 != null){
                   while (c2.moveToNext()){
                       int type = c2.getInt(c2.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));
                       if(type == ContactsContract.CommonDataKinds.Email.TYPE_WORK){
                           Log.i("工作邮箱", c2.getString(c2.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA)));
                       }
                   }
               }
               c2.close();*/
            }
        }
        cursor.close();


    }
}