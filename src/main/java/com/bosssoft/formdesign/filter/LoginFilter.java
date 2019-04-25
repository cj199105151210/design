//package com.bosssoft.formdesign.filter;
//
//import com.alibaba.druid.util.StringUtils;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import org.springframework.http.HttpStatus;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
//import org.springframework.stereotype.Component;
//
///**
//  * @类描述： 登录过滤器
//  * @类名称： LoginFilter
//  * @创建人： fw
//  * @创建时间： 2018-12-19
//  */
//
//@Component
//public class LoginFilter extends ZuulFilter{
//
//   //过滤器类型前置
//    @Override
//    public String filterType() {
//        return FilterConstants.PRE_TYPE;
//    }
//
//    //过滤器执行顺序
//    @Override
//    public int filterOrder() {
//        return 4;
//    }
//
//    /**
//     * 过滤器生效
//     * @return
//     */
//    @Override
//    public boolean shouldFilter() {
//
//        RequestContext  requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request =  requestContext.getRequest();
//
//        return false;
//    }
//
//    @Override
//    public Object run() {
//
//        RequestContext  requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request =  requestContext.getRequest();
//        String token = request.getHeader("token");
//        if (StringUtils.isEmpty(token))
//        {
//            token = request.getParameter("token");
//        }
//
//        if (StringUtils.isEmpty(token))
//        {
//            requestContext.setSendZuulResponse(false);
//            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//        }
//        return null;
//    }
//}
