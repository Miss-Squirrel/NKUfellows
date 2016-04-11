package nkubs.practice;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class P_ViewPager extends Activity implements ViewPager.OnPageChangeListener{

    private List<View> viewList;
    private ViewPager pager;
    private PagerTabStrip tabStrip;
    private List<String> tabList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_viewpager);
        viewList = new ArrayList<>();


        tabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
        tabList = new ArrayList<>();
        tabList.add("第一页");
        tabList.add("第二页");
        tabList.add("第三页");
        tabList.add("第四页");
        //为PagerTabStrip设置属性
        tabStrip.setBackgroundColor(Color.YELLOW);
        tabStrip.setTextColor(Color.BLACK);
        tabStrip.setDrawFullUnderline(false);
        tabStrip.setTabIndicatorColor(Color.GREEN);


        //将layout文件转为View对象，以做ViewPager的数据源
        View view1 = View.inflate(this, R.layout.p_viewpager1, null);
        View view2 = View.inflate(this, R.layout.p_viewpager2, null);
        View view3 = View.inflate(this, R.layout.p_viewpager3, null);
        View view4 = View.inflate(this, R.layout.p_viewpager4, null);

        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewList.add(view4);

        pager = (ViewPager) findViewById(R.id.viewpager);

        //建立适配器
        ViewPagerAdapter adapter = new ViewPagerAdapter(viewList,tabList);

        //加载适配器
        pager.setAdapter(adapter);

        pager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(this,"当前是第"+ (position+1) +"个页面",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
