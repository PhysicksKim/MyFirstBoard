package hello.firstBoard;

import hello.firstBoard.config.JdbcConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Import(JdbcConfig.class)
@SpringBootApplication(scanBasePackages = "hello.firstBoard")
public class FirstBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstBoardApplication.class, args);
	}

	@Bean
	public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
		// 이 부분이 왜 있는지는 아래 글 참고
		// https://github.com/PhysicksKim/TIL/blob/main/Toyproject/MyFirstBoard/20221118_DELETE%EB%82%98PUT%EB%A9%94%EC%84%9C%EB%93%9C%EC%9D%B4%EC%9A%A9.md
		return new HiddenHttpMethodFilter();
	}

}
