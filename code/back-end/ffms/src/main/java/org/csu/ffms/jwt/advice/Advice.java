package org.csu.ffms.jwt.advice;

import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.csu.ffms.domain.Account;
import org.csu.ffms.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/9
 * @描述
 **/

//@ControllerAdvice
public class Advice implements ResponseBodyAdvice<Object> {
    @Autowired
    AccountService accountService;

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        List<String> passPath= new ArrayList<>(Arrays.asList("/account/login"));

        System.out.println(request.getURI().getPath());
        System.out.println( Pattern.matches(".*/account/login", request.getURI().toString()));
        if(!passPath.contains(request.getURI().getPath()) ){
            HttpHeaders headers = request.getHeaders();
            String token=headers.get("token").get(0);
            JSONObject userToken;
            userToken = JSONObject.parseObject(JWT.decode(token).getAudience().get(0));
            //过期时间
            userToken.put("exp",System.currentTimeMillis()+1000*60);
            //签发时间
            userToken.put("iat",System.currentTimeMillis());
//            Account account = new AccountService().getAccount((String)userToken.get("userid"));
            Account account=accountService.getAccount((String)userToken.get("userid"));
            String newToken= JWT.create().withAudience(JSONObject.toJSONString(userToken))
                    .sign(Algorithm.HMAC256(account.getPassword()));

            Cookie cookie= null;

                //cookie = new Cookie("token", URLEncoder.encode(JSONObject.toJSONString(userToken),"UTF-8"));
            cookie = new Cookie("token",newToken);

            ServletServerHttpResponse ssResp = (ServletServerHttpResponse)response;
            ssResp.getServletResponse().addCookie(cookie);
        }

        return body;
    }
}
