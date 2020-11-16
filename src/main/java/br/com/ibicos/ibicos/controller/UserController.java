package br.com.ibicos.ibicos.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibicos.ibicos.entity.User;
import br.com.ibicos.ibicos.repository.UserRepository;
	
	@RestController
	@RequestMapping(path = "user")
	public class UserController {
		
		@Autowired
		private UserRepository userRepository;
		
		@GetMapping()
		public Iterable<User> getuser() {
			return userRepository.findAll();
		}
		
		@GetMapping("/{id}")
		public Optional<User> getById(@PathVariable int id) {
			return userRepository.findById(id);
		}
		
		@PostMapping()
		public User addUser(@RequestBody User user) {
			userRepository.save(user);
			
			return user;

		}
	}
