package org.csu.ffms.service;

import org.csu.ffms.domain.Stock;
import org.csu.ffms.persistence.StockMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


}
