package com.alessio.coc;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class CoCApplication {

	public static void main(String[] args) {
//		SpringApplication.run(CoCApplication.class, args);
		SpringApplicationBuilder builder = new SpringApplicationBuilder(CoCApplication.class);
		builder.headless(false);
		/*ConfigurableApplicationContext context = */builder.run(args);

		ClashOfClansAPI api = new ClashOfClansAPI(new Constants());
		new Controller(api);
	}
}
