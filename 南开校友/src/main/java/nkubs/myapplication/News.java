package nkubs.myapplication;

import android.app.ActionBar;
import android.content.Intent;
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
    private int[] resID={R.drawable.p_202,R.drawable.p_204,R.drawable.p_205};
    private float starsX;
    private Bitmap result;
    static DBUtil dbUtil;
    private String add;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行

            if (message.what == 1) {
                Bitmap[] resBitmap = (Bitmap[]) message.obj;
                //遍历Bitmap[]，将Bitmap加载到imageView中，再将其加载到viewFlipper上
                for (Bitmap aResBitmap : resBitmap) {
                    ImageView imageView = new ImageView(News.this);
                    imageView.setImageBitmap(aResBitmap);
                    for (int i =0;i<resID.length;i++){
                        viewFlipper.addView(getImageView(i));
                    }
                    Log.i("主线程","加载imageView");
                }
            } else if (message.what == 2){
                Toast.makeText(News.this, "标题图片加载失败！", Toast.LENGTH_SHORT).show();
            }else if (message.what == 3){
                String add = (String) message.obj;
                Intent intent = new Intent();
                intent.putExtra("add", add);
                intent.setClass(News.this, News_detail.class);
                startActivity(intent);

            }else{
                Toast.makeText(News.this, "新闻内容加载失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.news);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); //enable 返回<
        actionBar.setTitle("南开新闻");

        listView = (ListView) findViewById(R.id.listView_news);
         /*SimpleAdapter(上下文，数据源（map所组成的list集合，每一个map对应listView中的一行,
        每一个map中的键必须包含所有在from中所指定的键），resource列表项的布局文件，from（map中的键名），
        to 绑定数据视图中的ID，与from组成对应关系)*/
        datalist = new ArrayList<Map<String,Object>>();
        simpleAdapter = new SimpleAdapter(this,getDataList(),R.layout.newslist,new String[]{"pic","theme"},new int[]{R.id.imageView_newslist,R.id.textView_newslist});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(this);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper_news);
        //以动态方式为ViewFlipper加入View

        dbUtil = new DBUtil();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入获取图片资源子线程");
                Bitmap[] resBitmap = new Bitmap[4];
                try{
                    for(int i = 0;i<4;i++){
                        int _Mid = 101+i;
                        System.out.println(_Mid);
                        resBitmap[i]=result;

                    }
                    Log.i("子线程", "传递");
                    System.out.println(resBitmap);
                    mhandler.obtainMessage(1,resBitmap).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();
    }



    private List<Map<String,Object>> getDataList()
    {

        Map<String, Object> map = new HashMap<String,Object>();
        map.put("pic",R.drawable.p_201);
        map.put("theme","校领导会见AACSB认证小组");
        datalist.add(map);
        Map<String, Object> map2 = new HashMap<String,Object>();
        map2.put("pic",R.drawable.p_202);
        map2.put("theme","中加水与环境安全联合研发中心落户南开");
        datalist.add(map2);
        Map<String, Object> map3 = new HashMap<String,Object>();
        map3.put("pic",R.drawable.p_203);
        map3.put("theme","中澳学者南开研讨中国资本市场创新");
        datalist.add(map3);
        Map<String, Object> map4 = new HashMap<String,Object>();
        map4.put("pic",R.drawable.p_204);
        map4.put("theme","“京津冀协同发展战略高端论坛”南开举行");
        datalist.add(map4);
        Map<String, Object> map5 = new HashMap<String,Object>();
        map5.put("pic",R.drawable.p_205);
        map5.put("theme","南开济南校友会举行成立大会");
        datalist.add(map5);
        Map<String, Object> map6 = new HashMap<String,Object>();
        map6.put("pic",R.drawable.p_203);
        map6.put("theme","津南校区“公能”农业体验园挂牌启用");
        datalist.add(map6);
        Map<String, Object> map7 = new HashMap<String,Object>();
        map7.put("pic",R.drawable.p_206);
        map7.put("theme","我校面向全球高薪诚聘英才");
        datalist.add(map7);

        return datalist;
    }



    @Override
    //position代表被点击对象所在listview位置，getItemAtPosition方法可以获得当前位置内容信息
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        new Thread(new Runnable() {
            public void run() {
                try{
                    add = dbUtil.getCont(position);
                    Log.i("信息", "" + add);
                    mhandler.obtainMessage(3,add).sendToTarget();
                }catch (Exception g){
                    mhandler.obtainMessage(4,null).sendToTarget();}
            }
        }).start();

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



    private ImageView getImageView(int i){
        ImageView imageView = new ImageView(this);
        //imageView.setImageResource(resID[i]); 这种方法显示的图片尺寸完全取决于原图片的尺寸
        imageView.setBackgroundResource(resID[i]); //这种可以自适应
        return imageView;
    }



}
