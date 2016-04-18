package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Fellows_Find extends Activity{

    private Button button1;
    private Button button2;
    private Button button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find);

        button1 = (Button) findViewById(R.id.button_find_accurate);
        button2 = (Button) findViewById(R.id.button_find_condition);
        button3 = (Button) findViewById(R.id.button_find_contacts);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                intent1.setClass(Fellows_Find.this, Fellows_Find_Accurate.class);
                startActivity(intent1);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                intent2.setClass(Fellows_Find.this, Fellows_Find_Condition.class);
                startActivity(intent2);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                intent3.setClass(Fellows_Find.this, Fellows_Find_Contacts.class);
                startActivity(intent3);
            }
        });
    }
}
