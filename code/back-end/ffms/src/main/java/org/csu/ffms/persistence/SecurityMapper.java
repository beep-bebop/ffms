package org.csu.ffms.persistence;

import org.csu.ffms.domain.Security;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Repository
public interface SecurityMapper {
    /**
     *@描述
     *@参数  number证券代码
     *@返回值  证券代码指代的证券
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    Security getSecurityBySecurityNumber(String number,String userid);

    /**
     *@描述
     *@参数  userid：用户唯一表示
     *@返回值  该用户的所有证券
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    List<Security> getSecurityByUserId(String userid);

    /**
     *@描述  在数据库中新增该用户的股票信息
     *@参数  证券对象信息
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void insertSecurity(Security security);

    /**
     *@描述 删除持股
     *@参数  证券对象
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void deleteSecurity(Security security);

    /**
     *@描述 修改持股
     *@参数  证券对象
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/7
     *@修改人和其它信息
     */
    void updateSecurity(Security security);


    /**
     *@描述 返回指定用户指定类型的证券
     *@参数 securitynumber:证券代号 userid：用户id type：交易类型
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    List<Security> getSecurityByType(String userid,String tradetype);


    /**
     *@描述 获取指定用户指定时间内的Security
     *@参数  userid:用户id，starttime：开始时间 endtime：结束时间
     *@返回值
     *@创建人  李振豪
     *@创建时间  2020/7/9
     *@修改人和其它信息
     */
    List<Security> getSecurityByTime(String userid,String starttime,String endtime);
}
