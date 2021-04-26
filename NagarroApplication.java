package nagarro.nagarroProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"nagarro.nagarroProject"})

public class NagarroApplication {

	public static void main(String[] args) {
		SpringApplication.run(NagarroApplication.class, args);
	}

}
