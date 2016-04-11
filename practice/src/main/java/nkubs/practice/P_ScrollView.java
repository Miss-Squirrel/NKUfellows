package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;


public class P_ScrollView extends Activity {

    private TextView textView;
    private ScrollView scrollView;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_scrollview);
        textView = (TextView) findViewById(R.id.textview_scrollview);
        textView.setText(getResources().getString(R.string.content));
        tv = (TextView) findViewById(R.id.textview_include_title);
        tv.setText("ScrollView");

        scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_MOVE:
                    {
                        /*
                        * (1)getScrollY()--滚动条滑动的距离
                        * (2)getMeasuredHeight() 整个高度，包括未在屏幕显示的部分
                        * (3)getHeight() 屏幕高度
                        * */

                        //顶部状态
                        if(scrollView.getScrollY()<=0)
                        {
                            Log.i("信息","顶部");
                        }
                        if(scrollView.getScrollY()+scrollView.getHeight()>=
                            scrollView.getChildAt(0).getMeasuredHeight()) //scrollView包裹着textView
                        {
                            Log.i("信息","底部"+scrollView.getScrollY());
                            textView.append(getResources().getString(R.string.content));
                        }
                        break;
                    }
                }
                return false;
            }
        });

    }
}
