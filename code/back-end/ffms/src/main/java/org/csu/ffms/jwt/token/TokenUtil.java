package org.csu.ffms.jwt.token;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.csu.ffms.domain.Account;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/8
 * @描述
 **/
public class TokenUtil {

    //jwt生成token
    public static String getToken(Account account){
        String token="";
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userid",account.getUserid());
        //过期时间
        jsonObject.put("exp",System.currentTimeMillis()+1000*6000);
        //签发时间
        jsonObject.put("iat",System.currentTimeMillis());
        String json = JSONObject.toJSONString(jsonObject);
        token= JWT.create().withAudience(json)
                .sign(Algorithm.HMAC256(account.getPassword()));
        return token;
    }


}
