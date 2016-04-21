package nkubs.myapplication;


import android.util.Log;

import java.util.ArrayList;

public class DBUtil {

    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> brrayList = new ArrayList<String>();
    private ArrayList<String> resultist = new ArrayList<String>();

    private HttpConnSoap Soap = new HttpConnSoap();

    public Boolean signUpCheck_sid(int _sid, String name,String school,String birthdate){
        Log.i("信息","进入loginCheck方法");
        arrayList.clear();
        brrayList.clear();
        arrayList.add("_sid");
        arrayList.add("name");
        arrayList.add("school");
        arrayList.add("birthdate");

        brrayList.add(Integer.toString(_sid));
        brrayList.add(name);
        brrayList.add(school);
        brrayList.add(birthdate);

        resultist = Soap.GetWebService("signUpCheck_sid", arrayList, brrayList);
        return (resultist.get(0).equals("true"));

    }




}
