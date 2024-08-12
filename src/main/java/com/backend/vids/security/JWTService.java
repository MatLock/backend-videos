package com.backend.vids.security;

import com.backend.vids.model.JWTUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {
  private JwtParser jwtParser;
  private ObjectMapper mapper;

  public JWTService(ObjectMapper mapper,@Value("${app.jwt.key}") String secret){
    SecretKeySpec appSecret = new SecretKeySpec(secret.getBytes(), SignatureAlgorithm.HS512.getJcaName());
    this.jwtParser = Jwts.parser()
        .verifyWith(appSecret)
        .build();
    this.mapper = mapper;
  }

  public boolean isTokenInvalid(String auth){
    try{
      jwtParser.parse(auth);
    }catch (Exception e){
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @SneakyThrows
  public JWTUser getUser(String auth){
    JsonNode claims = mapper.valueToTree(jwtParser.parse(auth).getPayload());
    return mapper.treeToValue(claims, JWTUser.class);
  }

}
