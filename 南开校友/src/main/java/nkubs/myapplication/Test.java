package nkubs.myapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Arrays;


public class Test extends Activity {

    private Bitmap result;
    private DBUtil dbUtil = new DBUtil();
    private ImageView imageView;

    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message message) {//此方法在ui线程运行
            Bitmap[] resBitmap = (Bitmap[]) message.obj;

            if (message.what == 1) {

                    System.out.println(resBitmap[0]);
                    imageView.setImageBitmap(resBitmap[0]);

                    Log.i("主线程","加载imageView");
                    //imageView.setImageResource(resID[i]); 这种方法显示的图片尺寸完全取决于原图片的尺寸

            } else{
            Toast.makeText(Test.this, "标题图片加载失败！", Toast.LENGTH_SHORT).show();
        }
        }
    };






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        imageView = (ImageView) findViewById(R.id.imageView2);


        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("子线程","进入获取图片资源子线程");
                Bitmap[] resBitmap = new Bitmap[4];
                try{

                        int _Mid = 101;
                        System.out.println(_Mid);
                        result = dbUtil.imageResource(_Mid);
                        resBitmap[0]=result;

                    Log.i("子线程", "传递");
                    //加上.sendToTarget().啊啊啊啊啊
                    System.out.print("resBitmap");
                    System.out.println(Arrays.toString(resBitmap));
                    mhandler.obtainMessage(1,resBitmap).sendToTarget(); //message包含int what,和 Object 两个参数，还包含其他参数
                }catch (Exception h){   //通过catch提高系统容错能力，调试时适当注释掉，以便查看错误日志
                    mhandler.obtainMessage(2,null).sendToTarget();}
            }
        }).start();

    }
}
