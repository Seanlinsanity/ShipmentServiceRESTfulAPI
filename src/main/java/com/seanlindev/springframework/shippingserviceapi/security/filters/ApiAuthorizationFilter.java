package com.seanlindev.springframework.shippingserviceapi.security.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seanlindev.springframework.shippingserviceapi.dtos.AccessSecretDto;
import com.seanlindev.springframework.shippingserviceapi.security.SecurityConstants;
import com.seanlindev.springframework.shippingserviceapi.services.AccessSecretService;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class ApiAuthorizationFilter extends OncePerRequestFilter {

    private AccessSecretService accessSecretService;

    public void setAccessSecretService(AccessSecretService accessSecretService) {
        this.accessSecretService = accessSecretService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessKey = request.getHeader(SecurityConstants.API_ACCESS_KEY);
        String accessSignature = request.getHeader(SecurityConstants.API_ACCESS_SIGNATURE);
        String requestId = request.getHeader(SecurityConstants.API_REQUEST_ID);
        Boolean success = validateAccessSecrets(requestId, accessKey, accessSignature);
        if (!success) {
            handleNotAuthorizationResponse(response);
            return;
        }

        filterChain.doFilter(request, response);
        int httpStatus = response.getStatus();
        String httpMethod = request.getMethod();
        String uri = request.getRequestURI();
        String params = request.getQueryString();

        if (params != null) {
            uri += "?" + params;
        }

        System.out.println(String.join(" ", String.valueOf(httpStatus), httpMethod, uri));
    }

    private Boolean validateAccessSecrets(String requestId, String accessKey, String signature) {
        System.out.println("requestId: " + requestId + "; accessKey: " + accessKey + "; signature: " + signature);
        if (requestId == null || accessKey == null || signature == null) {
            return false;
        }

        String timestamp = requestId.substring(requestId.lastIndexOf(":") + 1);
        if (timestamp == null || timestamp.isEmpty()) {
            return false;
        }

        if ((Instant.now().getEpochSecond() - Long.valueOf(timestamp).longValue()) > SecurityConstants.API_ACCESS_EXPIRATION_TIME) {
            System.out.println("request time is expired");
            return false;
        }

        try {
            String keySecret = accessSecretService.getKeySecretByKeyId(accessKey).getKeySecret();
            String serverSignature = generateSignature(keySecret, requestId + "." + accessKey);
            System.out.println("server signature: " + serverSignature);
            return serverSignature.equals(signature);
        } catch (Exception e) {
            System.out.println("Access secret encoding error: " + e.getMessage());
            return false;
        }
    }

    private String generateSignature(String key, String data) throws Exception {
        Mac sha256_HMAC = Mac.getInstance(SecurityConstants.API_SIGN_ALGORITHM);
        SecretKeySpec secret_key = new SecretKeySpec(key.getBytes("UTF-8"), SecurityConstants.API_SIGN_ALGORITHM);
        sha256_HMAC.init(secret_key);

        byte[] encoded = Base64.getEncoder().encode(sha256_HMAC.doFinal(data.getBytes("UTF-8")));
        return new String(encoded);
    }

    private void handleNotAuthorizationResponse(HttpServletResponse response) throws ServletException, IOException {
        HttpServletResponse resp = (HttpServletResponse) response;
        resp.reset();
        resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        resp.setContentType("application/json; charset=utf-8");
        Map<String, Object> res = new HashMap<>();
        res.put("error", "Authorization failure. Unable to use the api service");
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
