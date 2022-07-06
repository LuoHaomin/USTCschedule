package com.edu.ustc.ustcschedule.web;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.JsonReader;
import android.util.JsonToken;

import androidx.preference.PreferenceManager;

import com.edu.ustc.ustcschedule.SQL.MainDatabaseHelper;
import com.edu.ustc.ustcschedule.SQL.MySchedule;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login {
    public static String LOGIN_URL = "https://passport.ustc.edu.cn/login?service=https%3A%2F%2Fjw.ustc.edu.cn%2Fucas-sso%2Flogin";
    public static String USER_AGENT = "User-Agent";
    public static String USER_AGENT_VALUE = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/94.0.4606.81 Safari/537.36 Edg/94.0.992.50";

    /*
    public static void main(String[] args) throws Exception {

        simulateLogin("", "", "2022年春季学期"); // 模拟登陆github的用户名和密码

    }*/


    /**
     * @param userName 用户名
     * @param pwd 密码
     * @throws Exception
     */
    public static ArrayList<Mycourse> simulateLogin(String userName, String pwd, Context context) throws Exception {

        ArrayList<Mycourse> ans = new ArrayList<Mycourse>();

        Connection con = Jsoup.connect(LOGIN_URL);  // 获取connection
        con.header(USER_AGENT, USER_AGENT_VALUE);
        con.header("Connection","keep-alive");
        con.header("Content-Type","application/x-www-form-urlencoded;charset=utf-8");// 配置模拟浏览器
        con.header("sec-ch-ua","\"Chromium\";v=\"94\", \"Microsoft Edge\";v=\"94\", \";Not A Brand\";v=\"99\"");
        con.header("sec-ch-ua-mobile","?0");
        con.header("Pragma","no-cache");
        con.header("Cache-Control","no-cache");
        con.header("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");
        con.header("Referer","https://passport.ustc.edu.cn/login?service=https%3A%2F%2Fyoung.ustc.edu.cn%2Flogin%2Fsc-wisdom-group-learning%2F");
        //con.header("Host","www.ustc.edu.cn");
        Response rs = con.execute();                // 获取响应
        Document d1 = Jsoup.parse(rs.body());       // 通过Jsoup将返回信息转换为Dom树
        List<Element> eleList = d1.select("form");  // 获取提交form表单，可以通过查看页面源码代码得知
        //System.out.println(rs.body());

        Map<String, String> datas = new HashMap<>();

        String rawCAS_LT = rs.parse().select("form").select("input[name=CAS_LT]").toString();
        int a = rawCAS_LT.indexOf("value");
        int b = rawCAS_LT.lastIndexOf("\"");
        String CAS_LT = rawCAS_LT.substring(a + 7, b);
        //System.out.println(rawCAS_LT);
        //System.out.println(CAS_LT);
        //System.out.println(rs.cookies());

        datas.put("model","uplogin.jsp");
        datas.put("CAS_LT",CAS_LT);
        datas.put("service","https://jw.ustc.edu.cn/ucas-sso/login");
        datas.put("warn","");
        datas.put("showCode","");
        datas.put("qrcode","");
        datas.put("username",userName);
        datas.put("password",pwd);
        datas.put("button","");

        Connection con2 = Jsoup.connect("https://passport.ustc.edu.cn/login?service=https%3A%2F%2Fjw.ustc.edu.cn%2Fucas-sso%2Flogin");
        con2.header(USER_AGENT, USER_AGENT_VALUE);
        // 设置cookie和post上面的map数据
        Response login = con2.ignoreContentType(true).followRedirects(true).method(Method.POST)
                .data(datas).cookies(rs.cookies()).execute();
        if(login.statusCode() != 200){
            ans.add(new Mycourse( String.valueOf(login.statusCode()) ));
            //System.out.println(login.statusCode());
            return ans;
        } else if(login.body().indexOf("成功") == -1){
            ans.add( new Mycourse("账号或密码输入错误"));
            return ans;
        }
        //System.out.println(login.cookies());
        //System.out.println(login.body());

        Connection IDScon = Jsoup.connect("https://jw.ustc.edu.cn/for-std/course-table/").cookies(login.cookies());
        IDScon.header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        Document IDS = IDScon.ignoreContentType(true).followRedirects(true).method(Method.POST).data(datas).get();

        int a3 = IDS.toString().indexOf("var studentId = ");
        String stuId = IDS.toString().substring(a3+16,a3+22);
        //System.out.println(stuId);
        //int a4= IDS.toString().indexOf(semester);
        String iinffo = IDS.toString();
        Pattern p_seme = Pattern.compile("selected value=\"([0-9]+)\"" );
        String semeId = new String();
        Matcher seme_matcher =p_seme.matcher(IDS.toString());
        if(seme_matcher.find()) {
            semeId = seme_matcher.group();
            semeId=semeId.split("\"")[semeId.split("\"").length-1];
        }
        else
        {
            semeId="261";
        }
        /*if(a4 != -1){
            semeId = IDS.toString().substring(a4-5,a4-2);
        } else {
            ans.add(new Mycourse( "学期输入错误" ));
            return ans;
        }*/
        //stuId="var studentId = 030838;";
        //String semeId = new String("261");
        //System.out.println(semeId);
        //System.out.println(IDSrs.body());

        Connection Coursescon = Jsoup.connect("https://jw.ustc.edu.cn/for-std/course-table/semester/" + semeId + "/print-data/" + stuId).cookies(login.cookies());
        Coursescon.header("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
        Document Coursers = Coursescon.ignoreContentType(true).followRedirects(true).method(Method.POST).data(datas).get();
        //System.out.println(Coursers.body());

        //int index1 = 0, counts = 0;

        String Courses = Coursers.toString();

        XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
        XmlPullParser myParser = xmlFactoryObject.newPullParser();
        myParser.setInput(new ByteArrayInputStream(Courses.getBytes(StandardCharsets.UTF_8)),"UTF-8");
        int eventType = myParser.getEventType();

        String json_str=new String();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = myParser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if (name.equals("body")) {
                        json_str=myParser.nextText();
                        json_str=json_str.trim();
                    }
                    break;

                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = myParser.next();
        }
        Pattern p_id=Pattern.compile(":([0-9]+),");
        JsonReader jsonReader=new JsonReader(new StringReader(json_str));
        jsonReader.beginObject();
        String name=new String();
        while(jsonReader.hasNext())
        {
            name=jsonReader.nextName();
            if(name.equals("studentTableVm"))
            {
                jsonReader.beginObject();
                while(jsonReader.hasNext())
                {
                    while (jsonReader.hasNext()&&jsonReader.peek()!= JsonToken.NAME) {
                        jsonReader.skipValue();
                        name = jsonReader.nextName();
                        if(name.equals("name")) {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                            @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("name", jsonReader.nextString());
                            editor.apply();
                        }
                    }
                    if (!jsonReader.hasNext()) break;
                    name=jsonReader.nextName();
                    if(name.equals("activities"))
                    {
                        jsonReader.beginArray();
                        while(jsonReader.hasNext()) {
                            jsonReader.beginObject();
                            while (jsonReader.hasNext()) {

                                while (jsonReader.hasNext()&&jsonReader.peek()!= JsonToken.NAME) {
                                    jsonReader.skipValue();
                                }
                                if (!jsonReader.hasNext()) break;
                                name = jsonReader.nextName();
                                if(name.equals("courseName")){
                                    Mycourse course1 = new Mycourse();
                                    course1.setCourseName(jsonReader.nextString());
                                    ans.add(course1);
                                }
                                if(name.equals("weekday")){
                                    Mycourse course1=ans.get(ans.size()-1);
                                    course1.setWeekday(jsonReader.nextInt());
                                }
                                if(name.equals("startUnit")){
                                    Mycourse course1=ans.get(ans.size()-1);
                                    course1.setStartUnit(jsonReader.nextInt());
                                }
                                if(name.equals("endUnit")){
                                    Mycourse course1=ans.get(ans.size()-1);
                                    course1.setEndUnit(jsonReader.nextInt());
                                }
                                if(name.equals("room")){
                                    Mycourse course1=ans.get(ans.size()-1);
                                    course1.setRoom(jsonReader.nextString());
                                }
                                if(name.equals("teachers")) {
                                    Mycourse course1 = ans.get(ans.size() - 1);
                                    String teachers = new String();
                                    jsonReader.beginArray();
                                    while (jsonReader.hasNext()) {
                                        if(teachers.length()!=0)
                                        {
                                            teachers=teachers+",";
                                        }
                                        teachers=teachers+jsonReader.nextString();;
                                    }

                                    course1.setTeachers(teachers);
                                    jsonReader.endArray();

                                }
                            }
                            jsonReader.endObject();
                        }


                        jsonReader.endArray();

                    }

                }
                jsonReader.endObject();
            }
        }




        /*while (eventType != XmlPullParser.END_DOCUMENT)  {
            String name=myParser.getName();
            switch (eventType){
                case XmlPullParser.START_TAG:
                    if(name.equals("courseName")){
                        Mycourse course1 = new Mycourse();
                        course1.setCourseName(myParser.nextText());
                        ans.add(course1);
                    }
                    if(name.equals("weekday")){
                        Mycourse course1=ans.get(ans.size()-1);
                        course1.setWeekday(Integer.parseInt(myParser.nextText()));
                    }
                    if(name.equals("startUnit")){
                        Mycourse course1=ans.get(ans.size()-1);
                        course1.setStartUnit(Integer.parseInt(myParser.nextText()));
                    }
                    if(name.equals("endUnit")){
                        Mycourse course1=ans.get(ans.size()-1);
                        course1.setEndUnit(Integer.parseInt(myParser.nextText()));
                    }
                    if(name.equals("room")){
                        Mycourse course1=ans.get(ans.size()-1);
                        course1.setRoom(myParser.nextText());
                    }
                    if(name.equals("teachers")){
                        Mycourse course1=ans.get(ans.size()-1);
                        String teachers=new String();
                        int count_end_tag=1;//攒到2退出，遇到start_tag清零，初值为1以考虑没有老师的情况
                        while(count_end_tag<2)
                        {
                            eventType = myParser.next();
                            name=myParser.getName();
                            switch (eventType)
                            {
                                case XmlPullParser.START_TAG:
                                    count_end_tag=0;
                                    if(name.equals("teachers"))
                                    {
                                        if(teachers.length()!=0)
                                        {
                                            teachers=teachers+",";
                                        }
                                        teachers=teachers+myParser.nextText();
                                    }

                                    break;
                                case XmlPullParser.END_TAG:
                                    count_end_tag++;
                                    break;
                            }
                        }

                        course1.setTeachers(teachers);
                    }
                    break;

                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = myParser.next();
        }*/


        /*while((index1 = Courses.indexOf("courseName",index1)) != -1){
            Courses = Courses.substring(index1 + 13);
            int ending = Courses.indexOf("\"");
            course1.courseName = Courses.substring(0, ending);
            //System.out.println(course1.courseName);

            index1 = Courses.indexOf("room");
            Courses = Courses.substring(index1 + 6);
            if(Courses.indexOf("null") == 0){
                course1.room = "null";
            } else {
                ending = Courses.indexOf("\"");
                course1.room = Courses.substring(1, ending);
            }
            //System.out.println(course1.room);

            index1 = Courses.indexOf("weekday");
            Courses = Courses.substring(index1 + 9);
            ending = Courses.indexOf(",");
            course1.weekday = Integer.valueOf(Courses.substring(0, ending));
            //System.out.println(course1.weekday);

            index1 = Courses.indexOf("startUnit");
            Courses = Courses.substring(index1 + 11);
            ending = Courses.indexOf(",");
            course1.startUnit = Integer.valueOf(Courses.substring(0, ending));
            //System.out.println(course1.startUnit);

            index1 = Courses.indexOf("endUnit");
            Courses = Courses.substring(index1 + 9);
            ending = Courses.indexOf(",");
            course1.endUnit = Integer.valueOf(Courses.substring(0, ending));
            //System.out.println(course1.endUnit);

            counts++;
            ans.add(course1);
        }*/

        MainDatabaseHelper db_helper = new MainDatabaseHelper(context);
        SQLiteDatabase db = db_helper.getWritableDatabase();
        db.delete("SCHEDULE", "IMPORTANCE=?", new String[]{"4"});
        for(int i=0;i< ans.size();i++) {
            MySchedule schedule=ans.get(i).to_schedule();
            schedule.toDatabase(db);
        }

        return ans;
    }
}
