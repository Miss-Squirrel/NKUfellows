package nkubs.practice;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Administrator on 2016-04-09.
 */

public class P_WebOpen extends Activity {

    private WebView webView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_webopen);
        //直接调用系统安装的浏览器打开web页面
        //String url = "http://www.baidu.com";
        /*Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);*/
        init();
    }

    private void init() {
        webView = (WebView) findViewById(R.id.webView);
        //webView.loadUrl("file://android_asset");  打开本地页面
        //加载web资源
        webView.loadUrl("http://www.google.com");
        //覆盖WebView默认通过第三方或者系统浏览器打开网页的行为，使得网页可以在WebView中打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true，控制网页在WebView中打开，false则在第三方或系统浏览器中打开
                view.loadUrl(url);
                Log.i("页面", webView.getUrl()+"");
                return true;
            }
            //webViewClient帮助WebView去处理一些页面控制，和请求通知
        });


        WebSettings settings = webView.getSettings();
        //WebView加载页面优先使用缓存加载
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // newProgress 1-100间的整数.
                if (newProgress == 100) {
                    //页面加载完毕,关闭progressDialog
                    closeDialog();
                } else {
                    //网页正在加载,打开progressDialog
                    openDialog(newProgress);
                    Log.i("进度条", "加载");
                }
            }
        });
    }


    private void openDialog(int newProgress) {
        if(dialog==null)
        {
            dialog = new ProgressDialog(this);
            dialog.setTitle("正在加载");
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setProgress(newProgress);
            dialog.show();
        }else{
            dialog.setProgress(newProgress);
        }
    }
    private void closeDialog() {
        if(dialog != null && dialog.isShowing())
        {
            dialog.dismiss();
            dialog = null;
        }

    }


    //改写物理按键返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK)
        {
            //Toast.makeText(this, webView.getUrl(), Toast.LENGTH_SHORT).show();
            if (webView.canGoBack())
            {
                webView.goBack(); //返回上一页面
                return true;
            } else {
                System.exit(0); //退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
