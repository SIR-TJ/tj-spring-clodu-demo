package com.tj.spring.cloud.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//过滤器
//http://localhost:8766/api/a/hi?message=hello&token=1
@Component
public class LoginFilter extends ZuulFilter {
    /**
     * pre 路有前
     * routing 路由之时
     * post 路由之后
     * error 发生错误调用
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }


    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 是否需要过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 业务代码
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String token = request.getParameter("token");
        if(token == null){
            HttpServletResponse response = context.getResponse();
            response.setContentType("text/html;charset=utf-8");
            context.setSendZuulResponse(false);
            context.setResponseStatusCode(401);
            try {
                context.getResponse().getWriter().write("非法请求");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
