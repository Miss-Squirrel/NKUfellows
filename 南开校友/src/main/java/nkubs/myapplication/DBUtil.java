package nkubs.myapplication;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DBUtil {

    private ArrayList<String> arrayList = new ArrayList<String>();
    private ArrayList<String> brrayList = new ArrayList<String>();
    private ArrayList<String> resultist = new ArrayList<String>();


    private HttpConnSoap Soap = new HttpConnSoap();


    public Boolean signUpCheck_sid(int _sid, String name, String school, String birthdate) {
        Log.i("信息", "进入signUpCheck_sid方法");
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


    public Boolean insertUserInfo(int _sid, int gender, String city, String career, String mobile, String password, int pub) {
        Log.i("信息", "进入insertUserInfo方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();

        arrayList.add("_sid");
        arrayList.add("gender");
        arrayList.add("city");
        arrayList.add("career");
        arrayList.add("mobile");
        arrayList.add("password");
        arrayList.add("pub");

        brrayList.add(Integer.toString(_sid));
        brrayList.add(Integer.toString(gender));
        brrayList.add(city);
        brrayList.add(career);
        brrayList.add(mobile);
        brrayList.add(password);
        brrayList.add(Integer.toString(pub));

        resultist = Soap.GetWebService("insertUserInfo", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }


    public Boolean logInCheck(int _sid, String password) {
        Log.i("信息", "进入loginCheck方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid");
        arrayList.add("password");
        brrayList.add(Integer.toString(_sid));
        brrayList.add(password);
        resultist = Soap.GetWebService("logInCheck", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }


    public int find_mobile(String mobile) {
        Log.i("信息", "进入find_mobile方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("mobile");
        brrayList.add(mobile);
        resultist = Soap.GetWebService("find_mobile", arrayList, brrayList);
        return Integer.parseInt(resultist.get(0));
    }

    public int find_name(String name) {
        Log.i("信息", "find_name");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("name");
        brrayList.add(name);
        resultist = Soap.GetWebService("find_name", arrayList, brrayList);
        return Integer.parseInt(resultist.get(0));
    }


    public HashMap<String, String> targetSidInfo(int targetSid) {
        /*List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();*/
        HashMap<String, String> hashMap = new HashMap<String, String>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("targetSid");
        brrayList.add(Integer.toString(targetSid));

        //替换方法名啊！血淋淋的教训
        resultist = Soap.GetWebService("targetSidInfo", arrayList, brrayList);
       /* HashMap<String, String> tempHash = new HashMap<String, String>();
        tempHash.put("Cno", "Cno");
        tempHash.put("Cname", "Cname");
        tempHash.put("Cnum", "Cnum");
        list.add(tempHash);*/

        for (int j = 0; j < resultist.size(); j += resultist.size()) {
            hashMap.put("name", resultist.get(j).trim());
            hashMap.put("gender", resultist.get(j + 1).trim());
            hashMap.put("school", resultist.get(j + 2).trim());
            hashMap.put("major", resultist.get(j + 3).trim());
            hashMap.put("graduation", resultist.get(j + 4).trim());
            hashMap.put("city", resultist.get(j + 5).trim());
            hashMap.put("career", resultist.get(j + 6).trim());
            hashMap.put("mobile", resultist.get(j + 7).trim());
            Log.i("封装", resultist.get(j + 7));
            /*list.add(hashMap);*/
        }

        return hashMap;
    }


    public Boolean createRelation(int _sid1, int _sid2) {
        Log.i("信息", "进入发送好友请求方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid1");
        arrayList.add("_sid2");
        brrayList.add(Integer.toString(_sid1));
        brrayList.add(Integer.toString(_sid2));
        resultist = Soap.GetWebService("createRelation", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }


    public List<Map<String, Object>> friendsList(int _sid) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid");
        brrayList.add(Integer.toString(_sid));
        resultist = Soap.GetWebService("friendsList", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("friendSid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("city", resultist.get(j + 2));
            map.put("career", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }


    public List<Map<String, Object>> contactMatch(int _sid, List<String> list) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        String contact1 = list.get(0);
        String contact2 = list.get(1);
        String contact3 = list.get(2);

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid");
        arrayList.add("contact1");
        arrayList.add("contact2");
        arrayList.add("contact3");
        brrayList.add(Integer.toString(_sid));
        brrayList.add(contact1);
        brrayList.add(contact2);
        brrayList.add(contact3);
        resultist = Soap.GetWebService("contactMatch", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("target_sid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("city", resultist.get(j + 2));
            map.put("career", resultist.get(j + 3));
            datalist.add(map);
        }

        return datalist;

    }


    public Bitmap imageResource(int _Mid) {
        Bitmap bm = null;

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_Mid");
        brrayList.add(Integer.toString(_Mid));
        resultist = Soap.GetWebService("imageResource", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        byte[] b = resultist.get(0).getBytes();
        Log.i("获取byte", Arrays.toString(b));
        Log.i("获取byte", Integer.toString(b.length));
        if (b.length != 0) {
            Log.i("获取byte", "转换！");
            bm = BitmapFactory.decodeByteArray(b, 0, b.length);
        }

        return bm;

    }


    public Boolean sendMsg(int senSid, int recSid, String theme, String cont, String date) {
        Log.i("信息", "进入信息求方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("senSid");
        arrayList.add("recSid");
        arrayList.add("theme");
        arrayList.add("cont");
        arrayList.add("date");
        brrayList.add(Integer.toString(senSid));
        brrayList.add(Integer.toString(recSid));
        brrayList.add(theme);
        brrayList.add(cont);
        brrayList.add(date);
        resultist = Soap.GetWebService("sendMsg", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }


    public List<Map<String, Object>> recMsg(int recSid) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("recSid");
        brrayList.add(Integer.toString(recSid));
        resultist = Soap.GetWebService("recMsg", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_mid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("theme", resultist.get(j + 2));
            map.put("date", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }


    public HashMap<String, Object> recMsgDetail(int _mid) {

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_mid");
        brrayList.add(Integer.toString(_mid));
        resultist = Soap.GetWebService("recMsgDetail", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("name", resultist.get(0));
            map.put("senSid", resultist.get(1));
            map.put("theme", resultist.get(2));
            map.put("cont", resultist.get(3));
            map.put("date", resultist.get(4));

        return map;
    }


    public List<Map<String, Object>> senMsg(int senSid) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("senSid");
        brrayList.add(Integer.toString(senSid));
        resultist = Soap.GetWebService("senMsg", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_mid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("theme", resultist.get(j + 2));
            map.put("date", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }


    public HashMap<String, Object> senMsgDetail(int _mid) {

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_mid");
        brrayList.add(Integer.toString(_mid));
        resultist = Soap.GetWebService("senMsgDetail", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("name", resultist.get(0));
        map.put("recSid", resultist.get(1));
        map.put("theme", resultist.get(2));
        map.put("cont", resultist.get(3));
        map.put("date", resultist.get(4));

        return map;
    }


    public List<Map<String, Object>> findByCondition(String school, String graduation, String city, String career){

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("school");
        brrayList.add(school);
        arrayList.add("graduation");
        brrayList.add(graduation);
        arrayList.add("city");
        brrayList.add(city);
        arrayList.add("career");
        brrayList.add(career);
        resultist = Soap.GetWebService("findByCondition", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_mid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("city", resultist.get(j + 2));
            map.put("career", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }

    public int unreadMsg(int recSid) {
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("recSid");
        brrayList.add(Integer.toString(recSid));
        resultist = Soap.GetWebService("unreadMsg", arrayList, brrayList);
        return Integer.parseInt(resultist.get(0));
    }

    public List<Map<String, Object>> unrMsg(int recSid) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("recSid");
        brrayList.add(Integer.toString(recSid));
        resultist = Soap.GetWebService("unrMsg", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_mid", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("theme", resultist.get(j + 2));
            map.put("date", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }

    public int requestNum(int recSid) {
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("recSid");
        brrayList.add(Integer.toString(recSid));
        resultist = Soap.GetWebService("requestNum", arrayList, brrayList);
        return Integer.parseInt(resultist.get(0));
    }

    public List<Map<String, Object>> requestList(int _sid) {

        List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid");
        brrayList.add(Integer.toString(_sid));
        resultist = Soap.GetWebService("requestList", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        for (int j = 0; j < resultist.size(); j += 4) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("_sid1", resultist.get(j));
            map.put("name", resultist.get(j + 1));
            map.put("city", resultist.get(j + 2));
            map.put("career", resultist.get(j + 3));
            datalist.add(map);
        }
        return datalist;
    }

    public Boolean acceptRelation(int _sid1, int _sid2) {
        Log.i("信息", "进入接受好友请求方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid1");
        arrayList.add("_sid2");
        brrayList.add(Integer.toString(_sid1));
        brrayList.add(Integer.toString(_sid2));
        resultist = Soap.GetWebService("acceptRelation", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }

    public Boolean rejectRelation(int _sid1, int _sid2) {
        Log.i("信息", "进入拒绝好友请求方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid1");
        arrayList.add("_sid2");
        brrayList.add(Integer.toString(_sid1));
        brrayList.add(Integer.toString(_sid2));
        resultist = Soap.GetWebService("rejectRelation", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }

    public HashMap<String, String> _SidInfo(int _sid) {

        arrayList.clear();
        brrayList.clear();
        resultist.clear();
        arrayList.add("_sid");
        brrayList.add(Integer.toString(_sid));
        resultist = Soap.GetWebService("_SidInfo", arrayList, brrayList);

        //将网页返回值（ArrayList逐列装入Map中，用list封装）

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("city", resultist.get(0));
        map.put("career", resultist.get(1));
        map.put("mobile", resultist.get(2));
        map.put("pub", resultist.get( 3));

        return map;
    }

    public Boolean modifyUserInfo(int _sid, String city, String career, String mobile, int pub) {
        Log.i("信息", "进入修改用户信息方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();

        arrayList.add("_sid");
        arrayList.add("city");
        arrayList.add("career");
        arrayList.add("mobile");
        arrayList.add("pub");
        brrayList.add(Integer.toString(_sid));
        brrayList.add(city);
        brrayList.add(career);
        brrayList.add(mobile);
        brrayList.add(Integer.toString(pub));

        resultist = Soap.GetWebService("modifyUserInfo", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }

    public Boolean modifyPassword(int _sid, String password) {
        Log.i("信息", "进入修改用户信息方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();

        arrayList.add("_sid");
        arrayList.add("password");
        brrayList.add(Integer.toString(_sid));
        brrayList.add(password);

        resultist = Soap.GetWebService("modifyPassword", arrayList, brrayList);
        return (resultist.get(0).equals("true"));
    }

    public String myInvicode(int _sid) {
        Log.i("信息", "进入邀请码查询方法");
        arrayList.clear();
        brrayList.clear();
        resultist.clear();

        arrayList.add("_sid");
        brrayList.add(Integer.toString(_sid));

        resultist = Soap.GetWebService("myInvicode", arrayList, brrayList);

        return resultist.get(0).toString();

    }

}


