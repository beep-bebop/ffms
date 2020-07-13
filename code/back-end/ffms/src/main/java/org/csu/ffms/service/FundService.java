package org.csu.ffms.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.controller.GetJsonFromUrl;
import org.csu.ffms.domain.Fund;
import org.csu.ffms.persistence.FundMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Service
public class FundService {
    @Autowired
    private FundMapper fundMapper;

    public Fund getFundByFundCode(String code,String userid){
        return fundMapper.getFundByFundCode(code,userid);
    }

    public List<Fund> getFundByUserId(String userid){
        return fundMapper.getFundByUserId(userid);
    }

    public void insertFund(Fund fund){
        fundMapper.insertFund(fund);
    }

    public void deleteFund(Fund fund){
        fundMapper.deleteFund(fund);
    }

    public void updateFund(Fund fund){
        fundMapper.updateFund(fund);
    }

    public JSONArray getFundAPIInfoByUserid(String userid){
        String basicUrl="https://api.doctorxiong.club/v1/fund?code=";
        List<Fund> fundList=this.getFundByUserId(userid);
        for (Fund fund : fundList) {
            basicUrl=basicUrl+fund.getCode()+",";
        }
        basicUrl=basicUrl.substring(0,basicUrl.length()-1);
        JSONObject jsonObject=null;
        try {
            jsonObject=GetJsonFromUrl.GET(basicUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONArray data = jsonObject.getJSONArray("data");
        for(int i=0 ; i<data.size() ; ++i){
            JSONObject json=(JSONObject) data.get(i);
            json.put("userid",userid);
            String code=(String)json.get("code");
            for (Fund fund : fundList) {
                if(fund.getCode().equals(code)){
                    json.put("quantity",fund.getQuantity());
                    json.put("currentValue",fund.getQuantity()*Float.parseFloat((String)json.get("netWorth")));
                }
            }
        }
        return data;
    }
}