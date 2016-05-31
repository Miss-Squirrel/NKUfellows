package nkubs.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import java.util.ArrayList;


public class Association_Data_2_Career extends ActionBarActivity {

    private PieChart mChart;
    private float starsX;
    private DBUtil dbUtil;
    private int[] result;
    private int a;
    private int b;
    private int c;
    private int d;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            if (message.what == 1) {
                int[] res = new int[4];
                res = (int[]) message.obj;
                a = res[0];
                b = res[1];
                c = res[2];
                d = res[3];
                System.out.println(a+"+"+b+"+"+c+"+"+d);
                PieData mPieData = getPieData(4, 100);
                showChart(mChart, mPieData);
            }else{
                Toast.makeText(Association_Data_2_Career.this, "获取数据失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.association_data_piechart);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 校友会");


        Toast.makeText(Association_Data_2_Career.this,"可以左右滑动查看其他类别统计表",Toast.LENGTH_LONG).show();

        mChart = (PieChart) findViewById(R.id.spread_pie_chart);

        dbUtil = new DBUtil();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入子线程");
                try{
                    result = dbUtil.getData_Career();
                    Log.i("子线程", "传递");
                    System.out.println(result);
                    mhandler.obtainMessage(1,result).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();

    }



    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("");


        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
           /*mChart.setUnit(" €");
           mChart.setDrawUnitsInChart(true);*/

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);

        pieChart.setCenterText("行业分布比例");  //饼状图中间的文字
        pieChart.setCenterTextSize(20);

        pieChart.setDragDecelerationEnabled(true);

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        //mLegend.setEnabled(false);
        mLegend.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(16f);
        mLegend.setYEntrySpace(4f);
        mLegend.setTextSize(13);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }

    /**
     *
     * @param count 分成几部分
     * @param range
     */
    private PieData getPieData(int count, float range) {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容


        xValues.add("信息产业");  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4
        xValues.add("金融");
        xValues.add("工商管理");
        xValues.add("其他");

        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        /*float quarterly1 = a;
        float quarterly2 = 2;
        float quarterly3 = 2;
        float quarterly4 = 2;  */

        yValues.add(new Entry(a, 0)); //天津，绿色
        yValues.add(new Entry(b, 1)); //北京，紫色
        yValues.add(new Entry(c, 2)); //上海，蓝色
        yValues.add(new Entry(d, 3)); //其他，黄色

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues,""/*显示在比例图上*/);
        pieDataSet.setSliceSpace(2f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(188, 225, 182)); //绿色
        colors.add(Color.rgb(202, 185, 191)); //紫色
        colors.add(Color.rgb(73, 117, 164));  //蓝色
        colors.add(Color.rgb(241, 215, 154)); //黄色


        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 7 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
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
                    Toast.makeText(Association_Data_2_Career.this, "向前翻看前一页", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Association_Data_2_Career.this, Association_Data_1_City.class);
                    startActivity(intent);
                }
                else if (starsX-event.getX()>100)  //翻看后一页，向左滑动
                {
                    Toast.makeText(Association_Data_2_Career.this, "向后翻看后一页", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    intent.setClass(Association_Data_2_Career.this, Association_Data_3_Age.class);
                    startActivity(intent);
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


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.setClass(Association_Data_2_Career.this, Association.class);
                startActivity(intent);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}


