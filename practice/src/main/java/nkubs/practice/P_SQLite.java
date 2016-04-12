package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;


public class P_SQLite extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_sharedpreferences);
        //每个程序都有自己的数据库，默认情况下是各自互不干扰
        //创建一个数据库，并打开


    }
}
