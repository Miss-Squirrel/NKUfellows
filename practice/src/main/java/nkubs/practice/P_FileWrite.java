package nkubs.practice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class P_FileWrite extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.p_filewrite);

        final EditText editText = (EditText) findViewById(R.id.editText_filewrite);
        Button button = (Button) findViewById(R.id.button_filewrite);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                writeFile(text);
                Log.i("信息",readFile());
            }
        });

    }

    private void writeFile(String text){
        File f = Environment.getExternalStorageDirectory(); //获取存储卡地址
        File file = new File(f,"P_FileWrite");
        Log.i("信息", file.toString());
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.write(text.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String readFile(){
        File f = Environment.getExternalStorageDirectory(); //获取存储卡地址
        File file = new File(f,"P_FileWrite");
        String content = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        try {
            while ((len = fileInputStream.read(buffer)) != -1){
                byteArrayOutputStream.write(buffer,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        content = byteArrayOutputStream.toString();
        try {
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  content;
    }
}
