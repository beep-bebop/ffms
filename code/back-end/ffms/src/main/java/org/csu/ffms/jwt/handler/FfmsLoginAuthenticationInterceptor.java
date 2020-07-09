package org.csu.ffms.jwt.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.csu.ffms.domain.Account;
import org.csu.ffms.jwt.note.PassToken;
import org.csu.ffms.jwt.note.UserLoginToken;
import org.csu.ffms.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/8
 * @描述
 **/
@Component
public class FfmsLoginAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    AccountService accountService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {

        String token = request.getHeader("token");
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();
        if(method.isAnnotationPresent(PassToken.class))
        {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        String userid=null;
        if(method.isAnnotationPresent(UserLoginToken.class))
        {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if(userLoginToken.required())
            {
                if(token==null)
                {
                    //throw new RuntimeException("无token，请重新登录");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code","-1");
                    jsonObject.put("msg","无token，请重新登录");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }
                try{
                    userid = JWT.decode(token).getAudience().get(0);
                }
                catch (JWTDecodeException e){
                    //throw new RuntimeException("401");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code","-1");
                    jsonObject.put("msg","error token");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }
                Account account=accountService.getAccount(userid);
                if(account==null){
                    //throw new RuntimeException("用户不存在");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code","-1");
                    jsonObject.put("msg","用户不存在");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }

                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
                try{
                    jwtVerifier.verify(token);
                }
                catch (JWTVerificationException e){
                    //throw new RuntimeException("401");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("code","-1");
                    jsonObject.put("msg","错误的token");
                    writer.write(JSONObject.toJSONString(jsonObject));
                }

                return  true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("afterCompletion");
    }
}
