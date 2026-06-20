package com.prism.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NoCacheFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");
        chain.doFilter(request, response);
    }
}
