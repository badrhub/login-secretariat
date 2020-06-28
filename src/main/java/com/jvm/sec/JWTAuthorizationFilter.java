package com.jvm.sec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTAuthorizationFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest arg0, HttpServletResponse arg1, FilterChain arg2)
			throws ServletException, IOException {
		
		arg1.addHeader("Access-Control-Allow-Origin", "*");
		arg1.addHeader("Access-Control-Allow-Headers", "Origin ,Accept , X-Request-With , Content-Type ,Access-Control-Request-Method , Access-Control-Request-Headers , authorization");
		arg1.addHeader("Access-Control-Expose-Headers", "Access-Control-Allow-Origin , Content-Disposition , Access-Control-Allow-Credentials , authorization ");
		arg1.addHeader("Access-Control-Allow-Methods", "GET , POST , PUT , DELETE , PATCH");
		if(arg0.getMethod().equals("OPTIONS")) {
			arg1.setStatus(HttpServletResponse.SC_OK);
		}else if(arg0.getRequestURI().equals("/login")) {
			arg2.doFilter(arg0, arg1);
			return;
		}else {
		String jwt = arg0.getHeader(SecurityParams.JWT_HEADER);
		if(jwt == null || !jwt.startsWith(SecurityParams.TOKEN_PREFEX)) {
			arg2.doFilter(arg0, arg1);
		return;
		}
		JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SecurityParams.PRIVATE_SECRET)).build();
		String jwtToken = jwt.substring(SecurityParams.TOKEN_PREFEX.length());
		DecodedJWT decode = verifier.verify(jwtToken);
		String username = decode.getSubject();
		
		List<String> roles = decode.getClaims().get("roles").asList(String.class);
		Collection<GrantedAuthority>  authorities = new ArrayList<>();
		for(String r : roles) {
			authorities.add(new SimpleGrantedAuthority(r));
		}
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(username , null, authorities);
		SecurityContextHolder.getContext().setAuthentication(token);   
		arg2.doFilter(arg0, arg1);
		}
	}

	

}
