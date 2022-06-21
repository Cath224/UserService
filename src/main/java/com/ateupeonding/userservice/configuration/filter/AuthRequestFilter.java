package com.ateupeonding.userservice.configuration.filter;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

@Component
@Order(1)
public class AuthRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            header = header.replace("Bearer ", "");
            try {
                String login = JWTParser.parse(header)
                        .getJWTClaimsSet()
                        .getSubject();
                request.setAttribute("USER_LOGIN", login);
            } catch (ParseException e) {
                chain.doFilter(request, response);
            }
        }
        chain.doFilter(request, response);
    }

}
