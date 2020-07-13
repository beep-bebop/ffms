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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @创建人 ： 李振豪
 * @创建时间 2020/7/8
 * @描述
 **/
@Component
public class FfmsLoginAuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    AccountService accountService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler) throws Exception {

        String token = request.getHeader("token");
//        System.out.println("pre token:"+token);
//        token=URLDecoder.decode(token , "utf-8");
//        System.out.println("Decoder token :"+token);
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

        JSONObject userToken=null;
        //如果方法上有UserloginToken的注解，说需要验证token
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
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","无token，请重新登录");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }

                try{
                    userToken = JSONObject.parseObject(JWT.decode(token).getAudience().get(0)) ;
                }
                catch (JWTDecodeException e){
                    //throw new RuntimeException("401");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","Can't open token");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }

                Account account;
                try{
                    account=accountService.getAccount((String)userToken.get("userid"));
                }
                catch (NullPointerException e){
                    account=null;
                }

                if(account==null){
                    //throw new RuntimeException("用户不存在");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","account is not exist");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return false;
                }

                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(account.getPassword())).build();
                try{
                    jwtVerifier.verify(token);
                    System.out.println("success");
                }
                catch (JWTVerificationException e){
                    //throw new RuntimeException("401");
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","Error token,map error");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return  false;
                }

                //经过 token是否存在，能否取出键值对，token中userid用户是否存在，token是否正确等等,需要验证token的有效期
                System.out.println("用户："+userToken.get("userid"));
                System.out.println("签发时间："+sdf.format(new Date((long)userToken.get("iat"))));
                System.out.println("有效时间："+sdf.format(new Date((long)userToken.get("exp"))));
                if(System.currentTimeMillis()>(long)userToken.get("exp")){
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","Beyond the period of validity");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return  false;
                }
                else if(System.currentTimeMillis()<(long)userToken.get("iat"))
                {
                    PrintWriter writer=response.getWriter();
                    JSONObject jsonObject=new JSONObject();
                    jsonObject.put("status_code","-1");
                    jsonObject.put("msg","Before the time of issue");
                    writer.write(JSONObject.toJSONString(jsonObject));
                    return  false;
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
//        HandlerMethod handlerMethod = (HandlerMethod)handler;
//        Method method = handlerMethod.getMethod();
//        System.out.println("\n\nafterCompletion");
//        if(method.isAnnotationPresent(PassToken.class))
//        {
//            System.out.println("login function");
//            PassToken passToken = method.getAnnotation(PassToken.class);
//            if(passToken.required()){
//                return;
//            }
//        }
//        else{
//
//            String token = request.getHeader("token");
//            JSONObject userToken;
//            System.out.println("更新前："+token);
//            userToken = JSONObject.parseObject(JWT.decode(token).getAudience().get(0));
//            //过期时间
//            userToken.put("exp",System.currentTimeMillis()+1000*60);
//            //签发时间
//            userToken.put("iat",System.currentTimeMillis());
//            System.out.println("更新后："+userToken);
//            Cookie cookie=new Cookie("token",JSONObject.toJSONString(userToken));
//            response.addCookie(cookie);
//            System.out.println(cookie);
//        }

    }
}
