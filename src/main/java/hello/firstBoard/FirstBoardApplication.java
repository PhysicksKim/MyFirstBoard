package hello.firstBoard;

import hello.firstBoard.config.JdbcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(JdbcConfig.class)
@SpringBootApplication(scanBasePackages = "hello.firstBoard")
public class FirstBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstBoardApplication.class, args);
	}

}
