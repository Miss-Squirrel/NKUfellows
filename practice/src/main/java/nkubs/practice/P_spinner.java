package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016-04-09.
 */
public class P_spinner extends Activity implements AdapterView.OnItemSelectedListener{

    private TextView textView;
    private Spinner spinner;
    private ArrayAdapter arrayAdapter;
    private List<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_spinner);

        textView = (TextView) findViewById(R.id.textView_spinner);
        spinner = (Spinner) findViewById(R.id.spinner);

        //准备数据源
        list = new ArrayList<>();
        list.add("八里台本部");
        list.add("津南校区");
        list.add("泰达");
        list.add("迎水道校区");
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item,list);
        //设置一个下拉列表样式
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);

        //绑监听器！
        spinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        textView.setText( list.get(i) +"");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }



}
