package br.com.ibicos.ibicos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class IbicosApplication {

	public static void main(String[] args) {     
		SpringApplication.run(IbicosApplication.class, args);

	}

}
