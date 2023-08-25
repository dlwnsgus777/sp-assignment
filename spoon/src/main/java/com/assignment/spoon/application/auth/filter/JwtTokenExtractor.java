package com.assignment.spoon.application.auth.filter;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class JwtTokenExtractor {
   private static final String AUTHORIZATION = "Authorization";
   private static final String BEARER_TYPE = "Bearer";
   private static final String ACCESS_TOKEN_TYPE = JwtTokenExtractor.class.getSimpleName() + ".ACCESS_TOKEN_TYPE";

   private final HttpServletRequest request;

   public JwtTokenExtractor(HttpServletRequest request) {
      this.request = request;
   }

   public String extract() {
      Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
      while (headers.hasMoreElements()) {
         String value = headers.nextElement();
         if ((value.toLowerCase().startsWith(BEARER_TYPE.toLowerCase()))) {
            String authHeaderValue = value.substring(BEARER_TYPE.length()).trim();
            request.setAttribute(ACCESS_TOKEN_TYPE, value.substring(0, BEARER_TYPE.length()).trim());
            int commaIndex = authHeaderValue.indexOf(',');
            if (commaIndex > 0) {
               authHeaderValue = authHeaderValue.substring(0, commaIndex);
            }
            return authHeaderValue;
         }
      }
      return null;
   }
}
