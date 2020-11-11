package br.com.ibicos.ibicos.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {
	
	@Value("${security.jwt.expiration-time}")
	private String expirationTime;
	
	@Value("${security.jwt.sign-key}")
	private String signKey;

	
	public String generateToken(User user) {
		LocalDateTime expirationDateAndTime = LocalDateTime.now()
				.plusMinutes(Long.valueOf(expirationTime));
		
		Date expirationDate = Date.
				from(expirationDateAndTime.atZone(ZoneId.systemDefault()).toInstant());
		
		return	Jwts.builder()
				.setSubject(user.getEmail())
				.setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, signKey)
				.compact();
	}
	
	private Claims getClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(signKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean isTokenValid(String token) {
		try {
			Claims claims = getClaims(token);
			LocalDateTime expirationDateTime = claims.getExpiration()
					.toInstant()
					.atZone(ZoneId.systemDefault())
					.toLocalDateTime();
			
			return !LocalDateTime.now().isAfter(expirationDateTime);			
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getEmailUser(String token) throws ExpiredJwtException {
		return getClaims(token).getSubject();		
	}

}
