package org.csu.ffms.service;

import ch.qos.logback.classic.spi.STEUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.csu.ffms.controller.GetJsonFromUrl;
import org.csu.ffms.domain.Account;
import org.csu.ffms.domain.Fund;
import org.csu.ffms.domain.Stock;
import org.csu.ffms.persistence.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Service
public class StockService {
    @Autowired
    private StockMapper stockMapper;

    public Stock getStockByStockCode(String code,String userid){
        return stockMapper.getStockByStockCode(code,userid);
    }

    public List<Stock> getStockByUserId(String userid){
        return stockMapper.getStockByUserId(userid);
    }

    public void insertStock(Stock stock){
        stockMapper.insertStock(stock);
    }

    public void deleteStock(Stock stock){
        stockMapper.deleteStock(stock);
    }

    public void updateStock(Stock stock){
        stockMapper.updateStock(stock);
    }

    public List<Stock> getStockByFamilyId(String familyid){
        List<Stock> stockList=new ArrayList<>();
        return stockList;
    }

    public JSONArray getStockAPIInfoByUserid(String userid) {
        String basicUrl="https://api.doctorxiong.club/v1/stock/detail?code=";
        List<Stock> stockList=this.getStockByUserId(userid);
        JSONArray jsonArray=new JSONArray();
        for (Stock stock : stockList) {
            JSONObject stockInfoJson;
            try {
                stockInfoJson = (JSONObject) GetJsonFromUrl.GET(basicUrl+stock.getCode()).get("data");
                if(stockInfoJson!=null){
                    int quantity = stock.getQuantity();
                    stockInfoJson.put("userid",userid);
                    stockInfoJson.put("quantity",quantity);
                    BigDecimal price = new BigDecimal(stockInfoJson.get("price").toString());
                    stockInfoJson.put("currentValue",price.multiply(new BigDecimal(quantity)));
                    stockInfoJson.remove("dayMinData");
                    stockInfoJson.remove("dailyData");
                    jsonArray.add(stockInfoJson);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonArray;
    }

    //返回股票信息，不涉及用户。
    public JSONObject getStockAPIInfoByCode(String code){
        String basicUrl="https://api.doctorxiong.club/v1/stock/detail?code="+code;
        try{
            JSONObject jsonObject=(JSONObject) GetJsonFromUrl.GET(basicUrl).get("data");
            jsonObject.remove("dayMinData");
            jsonObject.remove("dailyData");
            return jsonObject;
        }
        catch (Exception e){
            return null;
        }
    }

}
