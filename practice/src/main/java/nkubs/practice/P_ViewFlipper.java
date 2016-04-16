package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class P_ViewFlipper extends Activity {

private ViewFlipper viewFlipper;
//地址是整型
private int[] resID={R.drawable.img_5,R.drawable.img_7,R.drawable.img_8,R.drawable.img_10};
private float starsX;


@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_viewflipper);

        viewFlipper = (ViewFlipper) findViewById(R.id.viewFlipper);
        //以动态方式为ViewFlipper加入View
        for (int i =0;i<resID.length;i++){
        viewFlipper.addView(getImageView(i));
        }
        /*
        viewFlipper.setInAnimation(this, R.anim.left_in); //设定切入动画效果样式
        viewFlipper.setOutAnimation(this, R.anim.left_out); //设定切出动画效果样式
        viewFlipper.setFlipInterval(10000); //设定动画切换间隔时长，数字单位为毫秒，1000 ms = 1s
        viewFlipper.startFlipping(); //动起来~~~
        */
        }

private ImageView getImageView(int i){
        ImageView imageView = new ImageView(this);
        //imageView.setImageResource(resID[i]); 这种方法显示的图片尺寸完全取决于原图片的尺寸
        imageView.setBackgroundResource(resID[i]); //这种可以自适应
        return imageView;
        }

@Override
public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
        //手指落下
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
