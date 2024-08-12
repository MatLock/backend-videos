package com.backend.vids.utils;

public class Constant {

  public static final String AUTH_BEARER = "Bearer ";
  public static final String AUTH_HEADER = "Authorization";

  public static final String HTTP_401_HEADER_NOT_FOUND = "No Auth Header Found";
  public static final String HTTP_401_INVALID_HEADER = "Invalid Auth Header";
  public static final String HTTP_401_TOKEN_INVALID = "Auth Token could not be verified";

  public static final String HTTP_400_ALREADY_EXISTS = "%s Already Exists";
  public static final String HTTP_404_NOT_FOUND = "Not Found";
  public static final String HTTP_403_USER_FORBIDDEN_TO_FOLDER = "User has not access to folder";


  public static final String HTTP_400_INVALID_ROLE = "%s role invalid";
  public static final String HTTP_400_ROLE_EMPTY = "Role list cannot be empty or null";
}
