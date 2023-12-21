package com.jwtDemo.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jwtDemo.JWT.JwtUtils;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private UserDetailsService userDetailsService; 
	
	
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		//1. read token from auth head
		String token = request.getHeader("Authorization");
		
		//2. username should not be empty,context authentication must be empty
		if(token!=null) {
			//do validation
			String username = jwtUtils.getUsername(token);
			if(username!= null && SecurityContextHolder.getContext()
					.getAuthentication()==null) 
			{
			    UserDetails user = userDetailsService.loadUserByUsername(username);
				
				//validate token
				boolean valid = jwtUtils.validateToken(token,user.getUsername());
				if(valid) 
				{
					UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
							username,
							user.getPassword(),
							user.getAuthorities()
							);
				    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));	
				
				    //final object stored in SecurityContext with  User Details(un,pwd)
				    SecurityContextHolder.getContext().setAuthentication(authToken);
				}
				
			}
		}
		
		filterChain.doFilter(request, response);
		
	}

}
