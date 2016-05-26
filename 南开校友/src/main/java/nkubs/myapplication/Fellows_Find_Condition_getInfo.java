package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;
import java.util.Map;


public class Fellows_Find_Condition_getInfo extends Activity {

    private EditText et_school;
    private EditText et_graduation;
    private EditText et_city;
    private EditText et_career;
    private String school ;
    private String graduation ;
    private String city ;
    private String career ;
    private Button bt_find;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fellows_find_condition);

        et_school = (EditText) findViewById(R.id.EditText_find_condition_school);
        et_graduation = (EditText) findViewById(R.id.EditText_find_condition_graduation);
        et_city = (EditText) findViewById(R.id.EditText_find_condition_city);
        et_career = (EditText) findViewById(R.id.EditText_find_condition_career);


        bt_find = (Button) findViewById(R.id.button_find_condition_find);
        bt_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                school = et_school.getText().toString();
                graduation = et_graduation.getText().toString();
                city = et_city.getText().toString();
                career = et_career.getText().toString();
                /*System.out.println(school+"");
                Log.i("city", city);*/
                Intent intent = new Intent();
                intent.setClass(Fellows_Find_Condition_getInfo.this, Fellows_Find_Condition_list.class);
                intent.putExtra("school", school);
                intent.putExtra("graduation", graduation);
                intent.putExtra("city", city);
                intent.putExtra("career", career);
                startActivity(intent);


            }
        });



    }
}
