package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Post;
import hello.firstBoard.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

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

        String sql = "INSERT INTO BOARD(TITLE, WRITER, CONTENT) "
                + "VALUES(:title, :writer, :content)";
        SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, sqlParam, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        // keys 에는 "ID" "DATE" 두 가지 있음
//        log.info(keys.get("ID").toString());    // 예 : 3
//        log.info(keys.get("DATE").toString());  // 예 : 2022-08-21 21:21:14.793822

        return post;
    }

    @Override
    public Post getPost(long postId) {

        String sql = "SELECT * FROM BOARD where ID=:postId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("postId", postId);

        Post post = jdbcTemplate.queryForObject(sql, param, postRowMapper());

        return post;
    }

    @Override
    public List<Post> getPostList() {
        String sql = "SELECT ID, TITLE, WRITER, DATE FROM BOARD";

        try {
            List<Post> postList = jdbcTemplate.query(sql, postRowMapper());
            return postList;
        } catch (Exception e) {
            log.debug("ERROR : {} ; getPostList() query error",e);
        }
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

    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class);
    }
}
