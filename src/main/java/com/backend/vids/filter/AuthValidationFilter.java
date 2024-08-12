package com.backend.vids.filter;


import com.backend.vids.controller.reponse.ErrorRestResponse;
import com.backend.vids.model.JWTUser;
import com.backend.vids.security.CustomUserDetailsService;
import com.backend.vids.security.UserValidatorService;
import com.backend.vids.security.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static com.backend.vids.utils.Constant.AUTH_HEADER;
import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

public class AuthValidationFilter implements Filter {

  private static final String HTTP_401_JWT_TOKEN_NOT_VALID = "Auth Token Invalid";

  private static final String BEARER = "Bearer ";
  private ObjectMapper mapper;
  private JWTService jwtService;
  private CustomUserDetailsService customUserDetailsService;
  private UserValidatorService userValidatorService;

  public AuthValidationFilter(
      ObjectMapper mapper,
      JWTService jwtService,
      CustomUserDetailsService customUserDetailsService,
      UserValidatorService userValidatorService){
    this.mapper = mapper;
    this.jwtService = jwtService;
    this.customUserDetailsService = customUserDetailsService;
    this.userValidatorService = userValidatorService;
  }

  @Override
  @SneakyThrows
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;
    String authHeader = request.getHeader(AUTH_HEADER);
    String auth = authHeader.split(BEARER)[1];
    JWTUser user = jwtService.getUser(auth);
    if(!userValidatorService.authenticate(user.getSubject(), user.getSecret())){
      fillServletResponse(response,HTTP_401_JWT_TOKEN_NOT_VALID);
      return;
    }
    UserDetails userDetails = customUserDetailsService.loadUserByUsername(user.getSubject());

    UsernamePasswordAuthenticationToken authReq
        = new UsernamePasswordAuthenticationToken(user.getSubject(), "{noop}"+userDetails.getPassword(), userDetails.getAuthorities());
    SecurityContext sc = SecurityContextHolder.getContext();
    sc.setAuthentication(authReq);

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @SneakyThrows
  private void fillServletResponse(HttpServletResponse response, String reason){
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.getWriter().write(mapper.writeValueAsString(create401ErrorResponse(reason)));
    response.getWriter().flush();
  }

  private ErrorRestResponse create401ErrorResponse(String reason){
    return new ErrorRestResponse(null, reason);
  }
}
