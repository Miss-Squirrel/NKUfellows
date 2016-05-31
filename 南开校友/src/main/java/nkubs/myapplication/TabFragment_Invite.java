package nkubs.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TabFragment_Invite extends Fragment {

    private Button button;
    private EditText et_inviname;
    private EditText et_invicode;
    private EditText et_myname;
    private String inviname;
    private String invicode;
    private String myname;
    private DBUtil dbUtil;
    private int result;


    private android.os.Handler mhandler = new android.os.Handler() {
        public void handleMessage(Message msg) {//此方法在ui线程运行
            if(msg.what > 2) {
                Log.i("信息", "验证成功");
                Intent intent = new Intent();
                intent.putExtra("sid", msg.what);
                intent.setClass(getActivity(), SignUpFollow.class);
                startActivity(intent);
            }else if (msg.what == 1){
                Toast.makeText(getActivity(), "信息不匹配！", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(), "验证失败！", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.framgent_signupinvi,null);

        et_inviname = (EditText) view.findViewById(R.id.EditText_SignUpInvi_InviName);
        et_invicode = (EditText) view.findViewById(R.id.EditText_SignUpInvi_Code);
        et_myname = (EditText) view.findViewById(R.id.EditText_SignUpInvi_Name);
        dbUtil = new DBUtil();


        button = (Button) view.findViewById(R.id.button_SignUpInvi_Valid);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inviname = et_inviname.getText().toString();
                invicode = et_invicode.getText().toString();
                myname = et_myname.getText().toString();

                new Thread(new Runnable() {
                            public void run() {
                                try{
                                    result = dbUtil.signUpCheck_invi(inviname, invicode, myname);
                                    Log.i("信息", "" + result);
                                    if(result!=0) {
                                        mhandler.obtainMessage(result).sendToTarget();
                                    } else{
                                        mhandler.obtainMessage(1).sendToTarget();}
                                }catch (Exception f){
                                    mhandler.obtainMessage(2).sendToTarget();}
                            }
                        }).start();
            }
        });
        return view;
    }
}
