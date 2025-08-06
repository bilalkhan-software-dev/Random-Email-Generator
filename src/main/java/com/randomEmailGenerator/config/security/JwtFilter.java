package com.randomEmailGenerator.config.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.randomEmailGenerator.handler.GenericResponse;
import com.randomEmailGenerator.services.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import static com.randomEmailGenerator.util.Constants.AUTHORIZATION_HEADER;
import static com.randomEmailGenerator.util.Constants.BEARER_HEADER;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String authHeader = request.getHeader(AUTHORIZATION_HEADER);
            String token = null;
            String username = null;
            if (authHeader != null && authHeader.startsWith(BEARER_HEADER)) {

                token = authHeader.substring(7);
                log.info("BEARER_HEADER received");
                log.info("Token received : {}", token);
                username = jwtService.extractUsername(token);
                log.info("username received: {}", username);
                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    if (!userDetails.isEnabled()) {
                        throw new DisabledException("User account is disabled");
                    }


                    boolean isValidToken = jwtService.validateToken(token, userDetails);
                    if (isValidToken) {
                        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    }
                }
            }
        } catch (DisabledException e) {
            handleDisabledUser(response, e);
            return;
        } catch (Exception e) {
            handleJwtException(response, e);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private void handleDisabledUser(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.FORBIDDEN.value());
        Object errors = GenericResponse.builder().status("failed").message("User account is disabled").httpStatus(HttpStatus.FORBIDDEN).build().createResponse().getBody();
        response.getWriter().write(new ObjectMapper().writeValueAsString(errors));
    }


    private void handleJwtException(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Object errors = GenericResponse.builder().status("failed!").message(e.getMessage()).httpStatus(HttpStatus.UNAUTHORIZED).build().createResponse().getBody();
        response.getWriter().write(new ObjectMapper().writeValueAsString(errors));
    }
}
