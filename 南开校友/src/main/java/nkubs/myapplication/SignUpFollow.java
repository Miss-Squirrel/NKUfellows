package nkubs.myapplication;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class SignUpFollow extends Activity implements RadioGroup.OnCheckedChangeListener,AdapterView.OnItemSelectedListener{

    private RadioGroup rg;
    private Button su;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signupfollow);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowHomeEnabled(false); //移除icon

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("tag", isChecked + "");
                if (isChecked) {
                    Toast.makeText(SignUpFollow.this, "公开手机号", Toast.LENGTH_SHORT).show();
                    //获得checkbox的文本内容
                    String text = checkBox.getText().toString();
                    Log.i("tag", text);
                } else
                    Toast.makeText(SignUpFollow.this, "不公开手机号", Toast.LENGTH_SHORT).show();
            }
        });


        rg = (RadioGroup) findViewById(R.id.radiogroup);
        rg.setOnCheckedChangeListener(this);

        su = (Button) findViewById(R.id.button_signUp);
        su.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent("nkubs.myapplication.Login"));
            }
        });


        spinner = (Spinner) findViewById(R.id.Spinner_SignUpFollow_Career);
        //准备数据源
        list = new ArrayList<String>();
        list.add("金融");
        list.add("信息产业");
        list.add("工商管理");
        list.add("能源开采");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        //设置一个下拉列表样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //绑监听器！
        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //  checkedId为被选中状态RadioButton的id
        switch (checkedId) {
            case R.id.radioButton1:
                Log.i("tag", "男");
                break;
            default:
                Log.i("tag", "女");
                break;
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        /*textView.setText( list.get(i) +"");*/
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
