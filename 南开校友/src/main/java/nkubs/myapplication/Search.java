package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

/**
 * Created by Administrator on 2016-04-06.
 */

public class Search extends Activity implements CompoundButton.OnCheckedChangeListener {

    private AutoCompleteTextView acTextView;
    private String[] res = {"金融学", "金融工程", "管理科学与工程", "管理信息系统"};

    private ToggleButton tb;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.i("tag", "Search --> onCreate");
         /*
         * 初始化控件
         * 需要适配器
         * 初始化数据源--以用于匹配文本框输入的内容
         * 将adapter与当前AutoCompleteTextView绑定
         */
        acTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_list_item_1, res);
        acTextView.setAdapter(adapter);

        tb = (ToggleButton) findViewById(R.id.toggleButton);
        img = (ImageView) findViewById(R.id.imageView);

        /*
        给当前tb设置监听器，通过监听器判断开关状态
         */

        tb.setOnCheckedChangeListener(this);

        Intent intent = getIntent();
        //0为缺省值
        int sid = intent.getIntExtra("sid",0);
        Log.i("接收", sid+"");
        acTextView.setText(sid+"");
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
       /*
       * 当tb被点击的时候，当前的方法会执行
       * buttonView--代表被点击控件本身
         isChecked--代表被点击控件的状态
       *  当点击tb时候，更换image的背景
       */
        img.setBackgroundResource(isChecked ? R.drawable.on :
                R.drawable.off);
    }

}
