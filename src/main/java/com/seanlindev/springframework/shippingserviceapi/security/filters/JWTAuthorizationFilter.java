package com.seanlindev.springframework.shippingserviceapi.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seanlindev.springframework.shippingserviceapi.security.JWTHelper;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        Boolean success = JWTHelper.validateToken(token);
        if (!success) {
            handleJWTValidationFailure(response);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private void handleJWTValidationFailure(HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.reset();
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json; charset=utf-8");
        Map<String, Object> res = new HashMap<>();
        res.put("error", "Validation failure. Insufficient to grant access");
        res.put("code", 403);

        PrintWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(response.getOutputStream() , "UTF-8");
            writer = new PrintWriter(osw, true);
            ObjectMapper objectMapper = new ObjectMapper();

            String json = objectMapper.writeValueAsString(res);
            writer.write(json);
            writer.flush();
            writer.close();
            osw.close();
        } catch (UnsupportedEncodingException e) {
            System.out.println("Filter encoding error: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Filter write response error: " + e.getMessage());
        } finally {
            if (null != writer) {
                writer.close();
            }
            if(null != osw){
                osw.close();
            }
        }
    }
}
