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
@RestController
public class APIRequestController {
    @RequestMapping("/api")
    public JSONObject GetAPI(@RequestBody Map<String,Object> map){
        System.out.println(map);
        String url=(String)map.get("url");
        String method=(String)map.get("method");
        Map<String,String> jsonObject=(Map<String,String>) map.get("body");
        String body=JSONObject.toJSONString(jsonObject);
        System.out.println(url);
        System.out.println(method);
        System.out.println(body==null);
        JSONObject json;
        try{
            if(method.equals("get")){
                json = GetJsonFromUrl.GET(url);
            }
            else if(method.equals("post")){
                System.out.println(body);
                json=GetJsonFromUrl.POST(url,body);
            }
            else{
                json=new JSONObject();
                json.put("status_code",-200);
            }
        }
        catch (Exception e){
            json=new JSONObject();
            json.put("status_code",-200);
        }
        return json;
    }
}
