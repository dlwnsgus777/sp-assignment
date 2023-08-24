package com.assignment.spoon.application.auth.exception;

import com.assignment.spoon.common.utils.exceptions.CommonExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

   @Override
   public void commence(
         HttpServletRequest request,
         HttpServletResponse response,
         AuthenticationException e
   ) throws IOException {
      log.error("UnAuthorized -- message : " + e.getMessage());
      setResponse(request, response);
   }

   private void setResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {

      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

      Object message = request.getAttribute("message");
      if (message == null) {
         message = "인증 정보를 찾을 수 없습니다.";
      }
      String errorMessage = message.toString();

      Object code = request.getAttribute("code");
      if (code == null) {
         code = HttpServletResponse.SC_UNAUTHORIZED;
      }

      CommonExceptionHandler.ErrorResponse error = new CommonExceptionHandler.ErrorResponse((int) code, errorMessage);
      String result = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(error);

      response.getWriter().print(result);
   }
}
