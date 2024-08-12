package com.backend.vids.filter;

import com.backend.vids.controller.reponse.ErrorRestResponse;
import com.backend.vids.security.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;

import static com.backend.vids.utils.Constant.AUTH_BEARER;
import static com.backend.vids.utils.Constant.AUTH_HEADER;
import static com.backend.vids.utils.Constant.HTTP_401_HEADER_NOT_FOUND;
import static com.backend.vids.utils.Constant.HTTP_401_INVALID_HEADER;

public class AuthExistenceHeaderFilter implements Filter {

  private ObjectMapper objectMapper;
  private JWTService jwtService;

  public AuthExistenceHeaderFilter(ObjectMapper mapper, JWTService jwtService){
    this.objectMapper = mapper;
    this.jwtService = jwtService;
  }


  @Override
  @SneakyThrows
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String authHeader = request.getHeader(AUTH_HEADER);
    if(StringUtils.isBlank(authHeader)){
      fillServletResponse(response, HTTP_401_HEADER_NOT_FOUND);
      return;
    }

    if(jwtService.isTokenInvalid(authHeader.replaceAll(AUTH_BEARER,StringUtils.EMPTY))){
      fillServletResponse(response, HTTP_401_INVALID_HEADER);
      return;
    }
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @SneakyThrows
  private void fillServletResponse(HttpServletResponse response, String reason){
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(objectMapper.writeValueAsString(create401ErrorResponse(reason)));
    response.getWriter().flush();
  }

  private ErrorRestResponse create401ErrorResponse(String reason){
    return new ErrorRestResponse(null, reason);
  }
}
