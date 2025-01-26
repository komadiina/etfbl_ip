package org.unibl.etf.rest_api.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.unibl.etf.rest_api.service.APIKeyService;

@Configuration
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private APIKeyService apiKeyService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,  Object handler) {
        String username = request.getHeader("x-username");
        String apiKey = request.getHeader("x-api-key");

        boolean status = apiKeyService.isValid(username, apiKey);
        if (status) response.setStatus(HttpStatus.OK.value());
        else response.setStatus(HttpStatus.UNAUTHORIZED.value());

        return status;
    }
}
