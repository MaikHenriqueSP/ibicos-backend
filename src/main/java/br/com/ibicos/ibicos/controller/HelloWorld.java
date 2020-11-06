package br.com.ibicos.ibicos.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HelloWorld {
	

	@GetMapping
	public ResponseEntity<?> helloWorld() {
		return ResponseEntity.ok("olá mundo");
	}
	

}
