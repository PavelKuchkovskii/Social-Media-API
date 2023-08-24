package org.kucher.socialservice.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.kucher.socialservice.security.jwt.JwtTokenUtil;
import org.kucher.socialservice.security.jwt.exception.api.CustomJwtTokenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.apache.logging.log4j.util.Strings.isEmpty;

/**
 * A filter to intercept and process incoming HTTP requests to validate JWT tokens and authenticate users.
 */
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final ObjectMapper mapper;

    public JwtFilter(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (isEmpty(header) || !header.startsWith("Bearer ")) {
            chain.doFilter(request, response);
            return;
        }

        // Get jwt token and validate
        final String token = header.split(" ")[1].trim();


        try {
            // Get authentication and set it on the spring security context
            Authentication authentication = JwtTokenUtil.getAuthentication(token);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request, response);
        }
        catch (CustomJwtTokenException ex) {
            // Обработка ошибки и формирование ответа

            //Not using Message for the sake of flexibility
            //Using Map
            Map<String, String> error = new LinkedHashMap<>();
            error.put("logref", "error");
            error.put("message", ex.getMessage());

            String json = mapper.writeValueAsString(error);

            ResponseEntity<String> errorResponse = ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(json);
            response.setStatus(errorResponse.getStatusCodeValue());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(errorResponse.getBody());
        }

    }
}