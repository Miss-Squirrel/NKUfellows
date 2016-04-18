package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-04-18.
 */
public class News extends FragmentActivity implements AdapterView.OnItemClickListener,AbsListView.OnScrollListener{

    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> datalist;
    private ViewFlipper viewFlipper;
    //地址是整型
    private int[] resID={R.drawable.img_2,R.drawable.img_3,R.drawable.img_6};
    private float starsX;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle("南开新闻");

        listView = (ListView) findViewById(R.id.listView_news);
         /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
        datalist = new ArrayList<Map<String,Object>>();
        simpleAdapter = new SimpleAdapter(this,getDataList(),R.layout.newslist,new String[]{"pic","content"},new int[]{R.id.imageView_newslist,R.id.textView_newslist});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);
        listView.setOnScrollListener(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_news);
        //以动态方式为ViewFlipper加入View
        for (int i =0;i<resID.length;i++){
            viewFlipper.addView(getImageView(i));
        }


    }



    private List<Map<String,Object>> getDataList()
    {
        for (int i=0;i<20;i++) {
            Map<String, Object> map = new HashMap<String,Object>();
            map.put("pic",R.drawable.nkuicon);
            map.put("content","烧烤"+i);
            datalist.add(map);
        }
        return datalist;
    }
    @Override
    //position代表被点击对象所在listview位置，getItemAtPosition方法可以获得当前位置内容信息
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String text =listView.getItemAtPosition(position)+"";
        Toast.makeText(this, "position=" + position + text, Toast.LENGTH_SHORT).show();
    }
    @Override
    //scrollState为scrollState状态
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        switch (scrollState) {
            case SCROLL_STATE_FLING:
                Log.i("main", "用户手指在离开屏幕之前，由于用力的滑了一下，视图仍依靠惯性继续滑动");
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("pic", R.drawable.nkuicon);
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
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {}




    private ImageView getImageView(int i){
        ImageView imageView = new ImageView(this);
        //imageView.setImageResource(resID[i]); 这种方法显示的图片尺寸完全取决于原图片的尺寸
        imageView.setBackgroundResource(resID[i]); //这种可以自适应
        return imageView;}
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            //手指落下.
            case MotionEvent.ACTION_DOWN:
            {
                starsX = event.getX();
                break;
            }
            //手指滑动
            case MotionEvent.ACTION_MOVE:
            {
                //手指向右滑动,看前一页
                if(event.getX()-starsX > 100)
                {
                    viewFlipper.setInAnimation(this,R.anim.left_in);
                    viewFlipper.setOutAnimation(this,R.anim.left_out);
                    viewFlipper.showPrevious(); //显示前一页
                }
                else if (starsX-event.getX()>100)  //翻看后一页，向左滑动
                {
                    viewFlipper.setInAnimation(this,R.anim.right_in);
                    viewFlipper.setOutAnimation(this,R.anim.right_out);
                    viewFlipper.showNext();
                }
                break;
            }
            //手指抬起
            case MotionEvent.ACTION_UP:
            {
                break;
            }
        }
        return super.onTouchEvent(event);
    }



}
