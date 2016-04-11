package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-04-09.
 */
public class P_GridView extends Activity implements AdapterView.OnItemClickListener {

    private GridView gridView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String, Object>> dataList;
    private int[] icon = {R.drawable.icon_calculator,R.drawable.icon_calendar,R.drawable.icon_msg,
            R.drawable.icon_chrome,R.drawable.icon_camera,R.drawable.icon_dangdang,
            R.drawable.icon_douban,R.drawable.icon_facebook,R.drawable.icon_gmail,
            R.drawable.icon_health,R.drawable.icon_nkufellows,R.drawable.icon_video,};
    private String[] iconName = {"计算器", "日历", "短信", "谷歌浏览器", "相机", "当当",
            "豆瓣", "facebook", "邮箱", "健康", "南开校友", "视频"};


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_gridview);

        /*
        准备数据源 —> 新建适配器 -> GridView加载适配器 ->
        GridView配置事件监听器（onItemClickListener）
         */
        dataList = new ArrayList<>();
        simpleAdapter = new SimpleAdapter(this, getData(), R.layout.p_icons,
                new String[]{"pic", "content"}, new int[]{R.id.image_icon, R.id.textView_icon});

        gridView = (GridView) findViewById(R.id.gridView_1);
        gridView.setAdapter(simpleAdapter);

        //加载监听器！
        gridView.setOnItemClickListener(this);


    }

    private List<Map<String,Object>> getData(){

        for (int i =0;i<icon.length;i++)
        {   Map<String,Object> map= new HashMap<>();
            map.put("pic", icon[i]);
            map.put("content", iconName[i]);
            dataList.add(map);
        }
        return dataList;
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        //String text = gridView.getItemIdAtPosition(position)+"";
        Toast.makeText(this, "我是" + iconName[position], Toast.LENGTH_SHORT).show();
    }
}
