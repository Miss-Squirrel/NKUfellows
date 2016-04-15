package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;


public class P_FileSave extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.l_include);

        File f = Environment.getExternalStorageDirectory(); //获取存储卡地址
        File file = new File(f,"P_FileSave");
        Log.i("信息!", ""+f);

        /*if(!file.exists()){
            try {

                file.createNewFile();
                Toast.makeText(this, "文件已创建！", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            Log.i("信息","文件已存在！");
        file.delete();
    }*/

       /* File file = this.getFilesDir(); //得到当前程序默认的数据存储目录
        Log.i("信息", file.toString());*/

      /*  File file = this.getCacheDir(); //得到当前程序默认的缓存文件的存储目录，可以把一些不是很重要的文件在此存储，手机内存不足时，系统会自动清除
        Log.i("信息", file.toString());*/

        /*File file = this.getDir("Practice", MODE_PRIVATE);  //创建个文件夹
        Log.i("信息", file.toString());*/
    }
}
