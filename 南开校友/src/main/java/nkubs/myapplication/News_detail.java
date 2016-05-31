package nkubs.myapplication;


import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class News_detail extends ActionBarActivity{
    private WebView webView;
    private ProgressDialog dialog;
    private String add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.donation);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 南开新闻");

        //直接调用系统安装的浏览器打开web页面
        //String url = "http://www.baidu.com";
        /*Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intent);*/

        Intent intent = getIntent();
        add = intent.getStringExtra("add");
        init(add);

    }

    private void init(String add) {
        webView = (WebView) findViewById(R.id.webView_donation);
        //加载web资源
        webView.loadUrl(add);
        webView.setInitialScale(200);
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


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
