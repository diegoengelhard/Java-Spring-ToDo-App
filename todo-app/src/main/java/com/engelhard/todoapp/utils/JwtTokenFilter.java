package com.engelhard.todoapp.utils;

import com.engelhard.todoapp.models.entities.User;
import com.engelhard.todoapp.services.UserService;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
// import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    // Define Jwt Tools
    @Autowired
    private JwtTools jwtTools;

    // Define User Service
    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, java.io.IOException {
        try {
            String tokenHeader = request.getHeader("Authorization");
            String identifier = null;
            String token = null;

            if (tokenHeader != null && tokenHeader.startsWith("Bearer ") && tokenHeader.length() > 7) {
                token = tokenHeader.substring(7);
                try {
                    identifier = jwtTools.getUsernameFrom(token);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    System.out.println("JWT TOKEN has expired");
                } catch (MalformedJwtException e) {
                    System.out.println("Malformed JWT Token");
                }
            } else {
                System.out.println("Bearer string not found");
            }

            if (identifier != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                User user = userService.findByEmail(identifier);

                if (jwtTools.verifyToken(token)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            user, null, user.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            System.out.println(e.getMessage());
        }
    }

}
