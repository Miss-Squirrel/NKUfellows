package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

/**
 * Created by Administrator on 2016-04-09.
 */
public class P_progressBar extends Activity {

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //启用窗口特征，启用不带进度的进度条
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //显示进度条
        setProgressBarIndeterminateVisibility(true);
        setContentView(R.layout.p_progressbar);

        init();

    }

    private void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
