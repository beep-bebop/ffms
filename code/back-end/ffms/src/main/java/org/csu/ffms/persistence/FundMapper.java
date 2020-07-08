package org.csu.ffms.persistence;

import org.csu.ffms.domain.Fund;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Repository
public interface FundMapper {
    /**
     *@描述
     *@参数  code 为基金代码
     *@返回值  基金代码指代的股票
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    Fund getFundByFundCode(String code,String userid);

    /**
     *@描述
     *@参数  userid：用户唯一表示
     *@返回值  该用户的所有股票
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    List<Fund> getFundByUserId(String userid);

    /**
     *@描述  在数据库中新增该用户的基金信息
     *@参数  已购基金信息
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void insertFund(Fund Fund);

    /**
     *@描述 删除持有基金
     *@参数  基金
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void deleteFund(Fund Fund);

    /**
     *@描述 修改已购基金
     *@参数  基金
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void updateFund(Fund Fund);
}
