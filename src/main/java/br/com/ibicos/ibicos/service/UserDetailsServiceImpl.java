package br.com.ibicos.ibicos.service;

import br.com.ibicos.ibicos.dto.CredentialsDTO;
import br.com.ibicos.ibicos.domain.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final PasswordEncoder passwordEncoder;
	private final UserRepository userRepository;

	public UserDetailsServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository) {
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
	}

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
