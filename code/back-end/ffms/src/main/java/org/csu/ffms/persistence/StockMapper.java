package org.csu.ffms.persistence;

import org.csu.ffms.domain.Stock;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Repository
public interface StockMapper {
    /**
     *@描述
     *@参数  code 为股票代码
     *@返回值  股票代码指代的股票
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    Stock getStockByStockCode(String code,String userid);

    /**
     *@描述
     *@参数  userid：用户唯一表示
     *@返回值  该用户的所有股票
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    List<Stock> getStockByUserId(String userid);

    /**
     *@描述  在数据库中新增该用户的股票信息
     *@参数  持股信息
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void insertStock(Stock stock);

    /**
     *@描述 删除持股
     *@参数  股票
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void deleteStock(Stock stock);

    /**
     *@描述 修改持股
     *@参数  股票
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void updateStock(Stock stock);
}
