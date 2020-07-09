package org.csu.ffms.service;

import org.csu.ffms.domain.Security;
import org.csu.ffms.persistence.SecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/7
 * @描述
 **/
@Service
public class SecurityService {
    @Autowired
    private SecurityMapper securityMapper;

    public Security getSecurityBySecurityNumber(String number,String userid){
        return securityMapper.getSecurityBySecurityNumber(number,userid);
    }

    public List<Security> getSecurityByUserId(String userid){
        return securityMapper.getSecurityByUserId(userid);
    }

    public void insertSecurity(Security security){
        securityMapper.insertSecurity(security);
    }

    public void deleteSecurity(Security security){
        securityMapper.deleteSecurity(security);
    }

    public void updateSecurity(Security security){
        securityMapper.updateSecurity(security);
    }

    public List<Security> getSecurityByType(String userid,String tradetype){
        return securityMapper.getSecurityByType(userid,tradetype);
    }

    public List<Security> getSecurityByTime(String userid,String starttime,String endtime){
        return securityMapper.getSecurityByTime(userid,starttime,endtime);
    }

}
