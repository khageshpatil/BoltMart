package com.boltomart.auth_service.config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtTokenValidator extends OncePerRequestFilter {

	 private static final Logger logger = LoggerFactory.getLogger(JwtTokenValidator.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			Authentication existingAuth = SecurityContextHolder.getContext().getAuthentication();
			if (existingAuth != null && existingAuth.isAuthenticated()) {
				filterChain.doFilter(request, response);
				return;
			}

			String jwt = request.getHeader(JwtConstant.JWT_HEADER);
			if (jwt != null && jwt.startsWith("Bearer ")) {
				jwt = jwt.substring(7);

				SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());

				Claims claims = Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(jwt)
						.getBody();

				String email = claims.get("email", String.class);
				String authorities = claims.get("authorities", String.class);

				if (email != null && authorities != null) {
					List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
					Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auths);

					SecurityContextHolder.getContext().setAuthentication(authentication);
				}
			}
			filterChain.doFilter(request, response);
		} catch (Exception e) {
			logger.error("JWT validation error", e);
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			response.getWriter().write("Unauthorized: Invalid JWT token");
		}
	}

}
