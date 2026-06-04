package aep.SOSsego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SoSsegoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoSsegoApplication.class, args);
	}

}
