package org.csu.ffms.jwt.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.csu.ffms.domain.Account;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/8
 * @描述
 **/
public class TokenUtil {

    public static String getToken(Account account){
        String token="";
        token= JWT.create().withAudience(account.getUserid())
                .sign(Algorithm.HMAC256(account.getPassword()));
        return token;
    }
}
