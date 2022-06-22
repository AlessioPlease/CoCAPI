package com.alessio.coc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CoCApplication {

	public static void main(String[] args) {
//		SpringApplication.run(CoCApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CoCApplication.class);
		builder.headless(false);
		ConfigurableApplicationContext context = builder.run(args);

		View View = new View(new Constants());
	}
}
