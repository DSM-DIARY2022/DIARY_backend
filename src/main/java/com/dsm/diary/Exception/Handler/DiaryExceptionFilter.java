package com.dsm.diary.Exception.Handler;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DiaryExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (DiaryException e) {
            ErrorResponse errorResponse = new ErrorResponse(e.getStatus(), e.getMessage());
            response.setStatus(e.getStatus());
            response.setContentType("application/json");
            response.getWriter().write(errorResponse.toString());
        }
    }

}
