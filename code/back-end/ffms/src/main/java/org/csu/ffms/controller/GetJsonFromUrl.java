package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/12
 * @描述
 **/
public class GetJsonFromUrl {

    public static JSONObject GET(String url) throws IOException, JSONException
    {
        JSONObject json=new JSONObject();
        URL realUrl = new URL(url);
        URLConnection connection=realUrl.openConnection();
        InputStream inputStream=connection.getInputStream();
        try{
            BufferedReader rd = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            json = JSONObject.parseObject(jsonText);

        }
        catch (Exception e){
            json.put("status_code",-200);
            json.put("msg","API request error");
        }
        finally {
            inputStream.close();
        }
        return json;
    }

    public static JSONObject POST(String url, String body) throws IOException, JSONException {

        JSONObject json = new JSONObject();
        //新建URL对象realUrl，参数url定义在main方法
        URL realUrl = new URL(url);
        //调用openConnection赋值给URLConnection对象conn
        URLConnection conn = realUrl.openConnection();
        //设置是否从httpUrlConnection读入，默认情况下是true
        conn.setDoOutput(true);
        //设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在http正文内，因此需要设为true, 默认情况下是false;
        conn.setDoInput(true);
        //设置请求格式为JSON
        conn.setRequestProperty("Content-Type", "application/json");

        //把PringWriter对象会将字符串转换成字符编码数组再输出,getOutputStream按字节形式输出
        PrintWriter out = new PrintWriter(conn.getOutputStream());
        //输出body,POST请求的请求参数
        out.print(body);
        //用完Builder后，刷新缓存区，避免溢出
        out.flush();
        //读取url连接对象conn的数据并存instream
        InputStream instream = conn.getInputStream();

        try {
            //InputStreamReader对象，第一参数是InputStream in 输入流, 第二个是CharsetDecoder dec 编码格式)
            BufferedReader rd = new BufferedReader(new InputStreamReader(instream, Charset.forName("UTF-8")));
            //拿第一个方法的返回值然后存jsonText
            String jsonText = readAll(rd);
            json = JSONObject.parseObject(jsonText);
            return json; //返回json数据
        }
        catch (Exception e){
            json.put("status_code",-200);
            json.put("msg",e.toString());
        }
        finally {
            //关闭instream数据流
            instream.close();
        }
        return json;
    }

    /*
    在java中，String自创建起是一个不可变对象，每一次对他进行赋值，实际上是指向新的字符串。
    而 StringBuffer对象是一个可变的字符序列字符串，当一个 StringBuffer 被创建之后，可以通过
    append(),insert(),reverse().setCharAt(),setLength()等方法对该对象进行更改，一旦生成想要的字符串，课通过toString()转化为一个String对象
     StringBuiler 也代表课便字符串对象，StringBuilder和StringBuffer基本相似，两个类的构造器和方法也基本相同。不同的是：StringBuffer是线程安全的，而StringBuilder则没有实现线程安全功能，所以性能略高。
     */
    //readAll()从URLConnection的输入流读取字符，（输入流：能够读取一个字节序列的对象称作一个输入流），在请求第三方API过程中，我们需要读取第三方API返回的数据
    //因此是一个输入流，而我们在向第三方API接口写如参数时POST，则是输出流
    private static String readAll(Reader rd) throws IOException {

        StringBuilder sb = new StringBuilder();
        //用来判断读取状态
        int cp;
        //每个字节读取rd对象，cp为rd读取的字符编码，若读到最后就会返回-1，来判断是否文件读完
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);  //若读完了，把数据都添加进StringBuilder对象sb里面
        }
        return sb.toString();
    }


}
