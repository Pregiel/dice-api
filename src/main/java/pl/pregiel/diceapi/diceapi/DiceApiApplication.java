package pl.pregiel.diceapi.diceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import pl.pregiel.diceapi.diceapi.repository.UserRepository;
import pl.pregiel.diceapi.diceapi.service.UserService;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = {UserRepository.class})
public class DiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiceApiApplication.class, args);
	}

}

