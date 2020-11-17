package br.com.ibicos.ibicos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
		
	public UserDetails authenticate(CredentialsDTO credentials) {
		UserDetails userDetails = loadUserByUsername(credentials.getEmail());
		
		if(!userDetails.isEnabled()) {
			throw new DisabledException("Account is not yet confirmed, please validate it firt through your email");
		}
		
		if (passwordEncoder.matches(credentials.getPasswordUser(), userDetails.getPassword())) {
			return userDetails;
		}
		throw new BadCredentialsException("Incorrect email or password");
	}
		
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User loadedUser = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return org.springframework.security.core.userdetails.User
				.builder()
				.disabled(!loadedUser.getIsAccountConfirmed())
				.username(loadedUser.getEmail())
				.password(loadedUser.getPasswordUser())				
				.roles("USER")
				.build();
	}

}
