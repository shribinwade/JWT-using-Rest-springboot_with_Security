package com.jwtDemo.JWT;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;



@Component
public class JwtUtils {

	@Value("${app.secret}")
	private String secret;
	
	//1.Generate Token
	public String generateToken(String subject) {
		return Jwts.builder()
				   .setSubject(subject)
				   .setIssuer("shrihari")
				   .setIssuedAt(new Date (System.currentTimeMillis()))
				   .setExpiration(new Date(System.currentTimeMillis()+1000*60))
				   .signWith(SignatureAlgorithm.HS512,secret.getBytes())
				   .compact();
	}
	
	//2.Read Claims
	public Claims getClaims(String token) {
		return Jwts.parser()
				   .setSigningKey(secret.getBytes())
				   .parseClaimsJws(token)
				   .getBody();
						   
	}
	
	//3.Read Exp Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//4.Read subject/username
	public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
	
	//5.Validate Exp Date 
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		 boolean before = expDate.before(new Date (System.currentTimeMillis()));
		 System.out.println(before);
		 return before;
	}
	
	//6.validate username in token and database, expdate
	public boolean validateToken(String token ,String username) {
		 String tokenUserName = getUsername(token);
		 return (username.equals(tokenUserName) && !isTokenExp(token));
	}
	
}
