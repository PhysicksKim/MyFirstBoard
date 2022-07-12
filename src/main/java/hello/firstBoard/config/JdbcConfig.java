package hello.firstBoard.config;

import hello.firstBoard.repository.MemberRepository;
import hello.firstBoard.repository.MemberRepositoryPrototype;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class JdbcConfig {

    private final DataSource dataSource;

    @Bean
    public MemberRepository memberRepository() {
        return new MemberRepositoryPrototype(dataSource);
    }

}
