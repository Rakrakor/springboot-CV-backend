package com.samrak.CV.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.samrak.CV.service.UserDetailsServices;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
	
	//https://www.youtube.com/watch?v=X80nJ5T7YpE
	
	@Autowired
	private UserDetailsServices userDetailsServices;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain chain)
	throws ServletException, IOException{
		
		final String authorizationHeader=request.getHeader("Authorization");
		
		
		String username=null;
		String jwt=null;
		
		if(authorizationHeader !=null && authorizationHeader.startsWith("Bearer")) {
			jwt=authorizationHeader.substring(7);
			username=jwtUtil.extractUsername(jwt);
		}
		
		
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userDetails = this.userDetailsServices.loadUserByUsername(username);
			if(jwtUtil.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =new UsernamePasswordAuthenticationToken(
						userDetails,null,userDetails.getAuthorities());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}

		chain.doFilter(request, response);
		
	}

	
}
