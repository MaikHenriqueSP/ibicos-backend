package br.com.ibicos.ibicos.config;

import br.com.ibicos.ibicos.domain.service.JwtService;
import br.com.ibicos.ibicos.domain.service.UserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthFilter  extends OncePerRequestFilter {
	
	private JwtService jwtService;
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authorizationHeader = request.getHeader("Authorization");
		
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
			String token = authorizationHeader.split(" ")[1];
			
			if(jwtService.isTokenValid(token)) {
				String emailUser = jwtService.getEmailUser(token);
				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(emailUser);
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
						new UsernamePasswordAuthenticationToken(userDetails, null, new ArrayList<>());
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}			
		}
		filterChain.doFilter(request, response);
		
	}

}
