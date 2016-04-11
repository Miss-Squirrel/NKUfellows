package nkubs.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class SignUp extends Activity implements RadioGroup.OnCheckedChangeListener {

    private RadioGroup rg;
    private Button su;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("tag", isChecked + "");
                if (isChecked) {
                    Toast.makeText(SignUp.this, "感谢您的配合", Toast.LENGTH_SHORT).show();
                    //获得checkbox的文本内容
                    String text = checkBox.getText().toString();
                    Log.i("tag", text);
                } else
                    Toast.makeText(SignUp.this, "无法进行下一步", Toast.LENGTH_SHORT).show();
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
}
