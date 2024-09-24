package com.boltomart.auth_service.config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtTokenProvider {
	
	private SecretKey key=Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateAccessToken(Authentication auth) {
		return generateToken(auth, JwtConstant.ACCESS_TOKEN_VALIDITY);
	}

	public String generateRefreshToken(Authentication auth) {
		return generateToken(auth, JwtConstant.REFRESH_TOKEN_VALIDITY);
	}
	
	private String generateToken(Authentication auth, long expirationTime) {
		return Jwts.builder()
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + expirationTime))
				.claim("email", auth.getName())
				.claim("authorities", populateAuthorities(auth.getAuthorities()))
				.signWith(key)
				.compact();
	}
	
	public String getEmailFromJwtToken(String jwt) {
		Claims claims = parseToken(jwt);
		return String.valueOf(claims.get("email"));
	}
	
	private Claims parseToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();

	}
	
	public boolean validateToken(String authToken) {
		try {
			parseToken(authToken);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
		Set<String> auths=new HashSet<>();
		
		for(GrantedAuthority authority:collection) {
			auths.add(authority.getAuthority());
		}
		return String.join(",",auths);
	}
	

}

