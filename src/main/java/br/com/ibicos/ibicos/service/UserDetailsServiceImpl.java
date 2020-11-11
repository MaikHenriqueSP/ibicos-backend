package br.com.ibicos.ibicos.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User save(User user) {
		return userRepository.save(user);
	}
	
	public UserDetails authenticate(User user) {
		UserDetails userDetails = loadUserByUsername(user.getEmail());
		
		if (passwordEncoder.matches(user.getPasswordUser(), userDetails.getPassword())) {
			return userDetails;
		}
		throw new BadCredentialsException("Incorrect email or password");
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User loadedUser = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		User user = new User();
		user.setEmail(loadedUser.getEmail());
		user.setEmail(loadedUser.getPasswordUser());
		
		return org.springframework.security.core.userdetails.User
				.builder()
				.username(loadedUser.getEmail())
				.password(loadedUser.getPasswordUser())
				.build();
	}

}
