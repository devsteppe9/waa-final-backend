package edu.miu.waa;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
@SpringBootApplication
public class WaaApplication {

	public static ObjectMapper objectMapper = new ObjectMapper();

	public static void main(String[] args) {
		SpringApplication.run(WaaApplication.class, args);
	}
}
