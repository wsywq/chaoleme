package com.ywq.filter;

import com.alibaba.fastjson.JSON;
import com.ywq.common.BaseContext;
import com.ywq.common.ResponseTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Check login page
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
@Slf4j
public class LoginFilter implements Filter {
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    private static final String[] ALLOWED_LIST = new String[]{
            "/employee/login",
            "/employee/logout",
            "/backend/**",
            "/front/**",
            "/common/**",
            "/user/sendMsg",
            "/user/login",
            "/user/try",
            "/dish/**",
            "/category/**",
            "/order/**",
            "/weather/**"
    };

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String requestURI = request.getRequestURI();
        log.info("Client Filter requestURL: {}", requestURI);

        if (check(requestURI)) {
            log.info("requestURL is allowed");
            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("employee") != null) {
            log.info("This employee is login and id is {}", request.getSession().getAttribute("employee"));

            Long id = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentUserId(id);

            filterChain.doFilter(request, response);
            return;
        }

        if (request.getSession().getAttribute("user") != null) {
            log.info("This user is login and id is {}", request.getSession().getAttribute("user"));

            Long userId = (Long) request.getSession().getAttribute("user");
            BaseContext.setCurrentUserId(userId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("User is not login");
        response.getWriter().write(JSON.toJSONString(ResponseTemplate.error("NOTLOGIN")));
    }

    private boolean check(String requestURI) {
        for (String uri : ALLOWED_LIST) {
            if (PATH_MATCHER.match(uri, requestURI)) {
                return true;
            }
        }
        return false;
    }
}
