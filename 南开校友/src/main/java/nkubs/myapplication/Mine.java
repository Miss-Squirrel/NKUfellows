package nkubs.myapplication;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SpinnerAdapter;


public class Mine extends FragmentActivity {

    private Button bt_InfoChange;
    private Button bt_PwChange;
    private Button bt_MyInvicode;
    private Button bt_exit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); //enable 返回<
        actionBar.setTitle(" 主界面");


        bt_InfoChange = (Button) findViewById(R.id.button_Mine_InfoChange);
        bt_PwChange = (Button) findViewById(R.id.button_Mine_PwChange);
        bt_MyInvicode = (Button) findViewById(R.id.button_Mine_MyInvicode);
        bt_exit = (Button) findViewById(R.id.button_Mine_Exit);


        bt_InfoChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, Mine_InfoChange.class);
                startActivity(intent);
            }
        });

        bt_PwChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, Mine_PwChange.class);
                startActivity(intent);
            }
        });

        bt_MyInvicode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, Mine_MyInvicode.class);
                startActivity(intent);
            }
        });

        bt_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(Mine.this, Login.class);
                startActivity(intent);
            }
        });



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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}
