package org.csu.ffms;

import org.csu.ffms.controller.StockController;
import org.csu.ffms.domain.Security;
import org.csu.ffms.domain.Stock;
import org.csu.ffms.service.StockService;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/

@SpringBootTest
@MapperScan("org.csu.ffms.persistence")
class StockTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    StockService stockService;

    @Test
    public void testGetStockByStockCode(){
        System.out.println("get userid:1 and code:1 stock");
        Stock stock=stockService.getStockByStockCode("1","1");
        System.out.println(stock);
    }

    @Test
    public void testGetStockByUserId(){
        System.out.println("get userid:1 all stocks");
        List<Stock> stockList=stockService.getStockByUserId("50");
        System.out.println(stockList==null);
        for (Stock stock:stockList
             ) {
            System.out.println(stock.toString());
        }
    }
    @Test
    public void testInsertStock(){
        System.out.println("insert stock code:4 ,userid :1");
        Stock stock=new Stock("4","1","fourth",5,100);
        stockService.insertStock(stock);
        System.out.println("get stock by code:4 ,userid :1");
        System.out.println(stockService.getStockByStockCode("4","1"));
    }
    @Test
    public void testDeleteStock(){
        testGetStockByUserId();
        System.out.println("delete stock code:4 ,userid :1");
        Stock stock=new Stock("4","1","fourth",5,100);
        stockService.deleteStock(stock);
        testGetStockByUserId();
    }
    @Test
    public void testUpdateStock(){
        System.out.println("before update"+stockService.getStockByStockCode("1","1").toString());
        System.out.println("update stock code:1 ,userid :1");
        Stock stock=new Stock("1","1","first|fourth",55,10000);
        stockService.updateStock(stock);
        System.out.println("after update"+stockService.getStockByStockCode("1","1").toString());
    }



    @Autowired
    StockController stockController;

    @Test
    public void testStockAPI(){
        Map<String,String>map=new HashMap<>();
        map.put("userid","1");
        System.out.println(stockController.getStockTable(map));
        map.put("queryid","1");
        System.out.println(stockController.getStockTable(map));
    }

    @Test
    public void testStockController(){
        Map<String,String>map=new HashMap<>();
        map.put("userid","1");
        System.out.println(stockController.getTotal(map));
        map.put("queryid","1");
        System.out.println(stockController.getTotal(map));
    }
}