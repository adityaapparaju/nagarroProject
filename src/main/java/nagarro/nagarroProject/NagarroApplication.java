package nagarro.nagarroproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"nagarro.nagarroproject"})

public class NagarroApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagarroApplication.class, args);
	}

}
