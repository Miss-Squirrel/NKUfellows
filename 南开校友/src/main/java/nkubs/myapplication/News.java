package nkubs.myapplication;

import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class News extends FragmentActivity implements AdapterView.OnItemClickListener{


    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> datalist;
    private ViewFlipper viewFlipper;
    //地址是整型
    /* private int[] resID={R.drawable.img_2,R.drawable.img_3,R.drawable.img_6};*/
    private float starsX;
    private Bitmap result;
    private DBUtil dbUtil;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            Bitmap[] resBitmap = (Bitmap[]) message.obj;

            if (message.what == 1) {
                //遍历Bitmap[]，将Bitmap加载到imageView中，再将其加载到viewFlipper上
                for (Bitmap aResBitmap : resBitmap) {
                    ImageView imageView = new ImageView(News.this);
                    imageView.setImageBitmap(aResBitmap);
                    viewFlipper.addView(imageView);
                    Log.i("主线程","加载imageView");
                    //imageView.setImageResource(resID[i]); 这种方法显示的图片尺寸完全取决于原图片的尺寸
                }
            } else {
                Toast.makeText(News.this, "标题图片加载失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };




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

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_news);
        //以动态方式为ViewFlipper加入View
        dbUtil = new DBUtil();
     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入获取图片资源子线程");
                Bitmap[] resBitmap = new Bitmap[4];
                try{
                    for(int i = 0;i<4;i++){
                        int _Mid = 101+i;
                        System.out.println(_Mid);
                        result = dbUtil.imageResource(_Mid);
                        resBitmap[i]=result;

                    }
                    Log.i("子线程", "传递");
                    //加上.sendToTarget().啊啊啊啊啊
                    System.out.println(resBitmap);
                    mhandler.obtainMessage(1,resBitmap).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();*/


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
