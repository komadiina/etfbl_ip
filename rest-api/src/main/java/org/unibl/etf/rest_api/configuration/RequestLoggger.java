package org.unibl.etf.rest_api.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;

@Configuration
public class RequestLoggger implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  Object handler) {
        // log requests
        String username = request.getHeader("username");
        String method = request.getMethod();
        String endpoint = request.getRequestURI();

        System.out.printf("[%s] %s %s%n", username, method, endpoint);

        return true;
    }
}
