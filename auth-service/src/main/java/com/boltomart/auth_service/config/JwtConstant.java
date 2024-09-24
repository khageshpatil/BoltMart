package com.boltomart.auth_service.config;

public class JwtConstant {

	public static final String SECRET_KEY="wpembytrwcvnryxksdbqwjebruyGHyudqgwveytrtrCSnwifoesarjbwe";
	public static final String JWT_HEADER="Authorization";
	public static final long ACCESS_TOKEN_VALIDITY = 900000; // 15 minutes
	public static final long REFRESH_TOKEN_VALIDITY = 604800000;
	
}
