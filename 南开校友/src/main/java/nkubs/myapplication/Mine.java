package nkubs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;


public class Mine extends FragmentActivity {

    private Button bt_InfoChange;
    private Button bt_PwChange;
    private Button bt_MyInvicode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mine);

        bt_InfoChange = (Button) findViewById(R.id.button_Mine_InfoChange);
        bt_PwChange = (Button) findViewById(R.id.button_Mine_PwChange);
        bt_MyInvicode = (Button) findViewById(R.id.button_Mine_MyInvicode);


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



    }
}
