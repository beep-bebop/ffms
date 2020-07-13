package org.csu.ffms;

import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.controller.APIRequestController;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/12
 * @描述
 **/
@SpringBootTest
@MapperScan("org.csu.ffms.controller")
public class APIRequestControllerTests {
    @Autowired
    APIRequestController apiRequestController;

    @Test
    void getTests(){
//        Map<String,String> map=new HashMap<>();
//        map.put("url","https://api.doctorxiong.club/v1/fund?code=202015,007339");
//        map.put("method","get");
//        JSONObject json=apiRequestController.GetAPI(map);
//        System.out.println(json);
    }

    @Test
    void postTests(){
//        Map<String,String> map=new HashMap<>();
//        map.put("url","https://api.doctorxiong.club/v1/stock/rank");
//        map.put("method","post");
//        JSONObject requestJson = new JSONObject();
//        requestJson.put("sort","sell");
//        map.put("body",JSONObject.toJSONString(requestJson));
//        System.out.println(JSONObject.toJSONString(requestJson));
//        JSONObject json=apiRequestController.GetAPI(map);
//        System.out.println(json);
    }

    @Test
    void getBond(){
        Map<String,String> map=new HashMap<>();
        map.entrySet();

    }
}
