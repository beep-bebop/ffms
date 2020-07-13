package org.csu.ffms.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/12
 * @描述
 **/
//@RestController
//public class APIRequestController {
//    @RequestMapping("/api")
//    public JSONObject GetAPI(@RequestBody Map<String, String> map){
////        System.out.println(map);
//        String url=(String)map.get("url");
//        String method=(String)map.get("method");
//        Map<String,String> jsonObject=(Map<String, String>) map.get("body");
//        JSONObject json;
//        try{
//            if(method.equals("get")){
//                if(jsonObject.size()>0){
//                    url=url+"?";
//                    for(Map.Entry<String,String>entry:jsonObject.entrySet()){
//                        url=url+entry.getKey()+"="+entry.getValue()+"&";
//                    }
//                    url=url.substring(0,url.length()-1);
//                }
//                //System.out.println("url is : "+url);
//                json = GetJsonFromUrl.GET(url);
//            }
//            else if(method.equals("post")){
//                String body=JSONObject.toJSONString(jsonObject);
//                System.out.println(body);
//                json=GetJsonFromUrl.POST(url,body);
//            }
//            else{
//                json=new JSONObject();
//                json.put("status_code",-200);
//            }
//        }
//        catch (Exception e){
//            json=new JSONObject();
//            json.put("status_code",-200);
//        }
//        return json;
//    }
//
//    //债券api接口 http://web.juhe.cn:8080/fund/netdata/bond"
//
//
//}
