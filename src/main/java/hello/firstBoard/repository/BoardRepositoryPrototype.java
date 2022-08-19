package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Repository
@Slf4j
public class BoardRepositoryPrototype implements BoardRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public BoardRepositoryPrototype(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        log.debug("dataSource = {}", dataSource);
        try {
            log.debug("dataSource.getConnection() = {}", dataSource.getConnection());
        } catch (SQLException e) {
            log.debug("ERROR OCCURED ; datasource DI error ");
        }
    }

    @Override
    public Post save(Post post) {
        return null;
    }

    @Override
    public List<Post> getList() {
        return null;
    }

    @Override
    public Post update(Post post) {
        return null;
    }

    @Override
    public Post update(int postId) {
        return null;
    }

    @Override
    public Post delete(Post post) {
        return null;
    }

    @Override
    public Post delete(int postId) {
        return null;
    }
}
