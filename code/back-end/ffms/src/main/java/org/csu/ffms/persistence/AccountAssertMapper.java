package org.csu.ffms.persistence;

import org.csu.ffms.domain.AccountAssert;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/13
 * @描述
 **/
@Repository
public interface AccountAssertMapper {

    void insertAssert(AccountAssert accountAssert);

    List<AccountAssert>  getAssertByUserid(String userid);

    AccountAssert getLastAssertByUserid(String usreid);
}
