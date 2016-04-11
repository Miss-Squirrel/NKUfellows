package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{


    private ListView listView;
    private ArrayAdapter arrayAdapter;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> datalist;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        listView = (ListView) findViewById(R.id.listView);


        //ArrayAdapter(上下文，当前ListView加载的每一个列表项所对应的布局文件)
        String[] res = {"米线", "火锅", "大盘鸡拌面", "黄焖鸡米饭", "香辣牛肉拌饭", "牛肉干", "土豆粉"};
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, res);
        //listView.setAdapter(arrayAdapter);

        /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
        datalist = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this,getDataList(),R.layout.item,new String[]{"pic","content"},new int[]{R.id.imageView,R.id.textView});
        listView.setAdapter(simpleAdapter);


        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);
    }


    private List<Map<String,Object>> getDataList()
    {
        for (int i=0;i<20;i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("pic",R.drawable.ic_launcher);
            map.put("content","烧烤"+i);
            datalist.add(map);
        }
        return datalist;
    }


    @Override
    //i代表被点击对象所在listview位置，getItemAtPosition方法可以获得当前位置内容信息
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        String text =listView.getItemAtPosition(i)+"";
        Toast.makeText(this, "position=" + i + text, Toast.LENGTH_SHORT).show();
    }

    @Override
    //i为scrollState状态
    public void onScrollStateChanged(AbsListView absListView, int i) {
        switch (i) {
            case SCROLL_STATE_FLING:
                Log.i("main", "用户手指在离开屏幕之前，由于用力的滑了一下，视图仍依靠惯性继续滑动");
                Map<String, Object> map = new HashMap<>();
                map.put("pic", R.drawable.ic_launcher);
                map.put("content", "火锅");
                datalist.add(map);
                //告诉UI数据源发生变化，重新刷新
                simpleAdapter.notifyDataSetChanged();
                break;
            case SCROLL_STATE_IDLE:
                Log.i("main", "视图已经停止滑动");
                break;
            case SCROLL_STATE_TOUCH_SCROLL:
                Log.i("main","手指没有离开屏幕，正在滑动");
                break;
        }
    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {

    }
}
