package projects.todolistapp.core.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;

        String header = httpServletRequest.getHeader("Authorization");

        if(header == null || !header.startsWith("Bearer ")) {
            throw new ServletException("Missing or invalid authorization header.");
        } else {
            try {


                String token = header.substring(7);
                Claims claims = Jwts.parser().setSigningKey("byku123").parseClaimsJws(token).getBody();
                servletRequest.setAttribute("claims", claims);
            } catch (SignatureException e) {
                throw new ServletException("invalid token");
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}