package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Pagination;
import hello.firstBoard.domain.board.Post;
import lombok.extern.slf4j.Slf4j;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
    // ALTER TABLE BOARD ADD (DELETEFLAG boolean default true);
    @Override
    public Post save(Post post) {

        String sql = "INSERT INTO BOARD(TITLE, WRITER, CONTENT) "
                + "VALUES(:title, :writer, :content)";
        SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(post);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, sqlParam, keyHolder);

        Map<String, Object> keys = keyHolder.getKeys();
        post.setId(Long.parseLong(keys.get("ID").toString()));


        String sqlDateString = keys.get("DATE").toString();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        try {
            post.setDate(formatter.parse(sqlDateString));
            return post;
        } catch (ParseException e) {
            log.error("ERROR : {}",this.getClass());
            log.error("save() method Date Parse Error : " , e);
            return post;
        }
    }

    @Override
    public Post getPost(long postId) {
        String sql = "SELECT * FROM BOARD " +
                "WHERE ID=:postId AND DELETEFLAG=FALSE";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("postId", postId);

        return jdbcTemplate.queryForObject(sql, param, postRowMapper());
    }

    @Override
    public List<Post> getPostList(Pagination pagination) {
        String sql = "SELECT ID, TITLE, WRITER, DATE, HIT FROM BOARD " +
                "WHERE DELETEFLAG=FALSE " +
                "ORDER BY ID DESC LIMIT :start, :size";
        SqlParameterSource sqlParm = new MapSqlParameterSource()
                .addValue("start", pagination.getStartPost())
                .addValue("size", pagination.getPageSize());

        log.debug("start : {} , size : {} ", pagination.getStartPost(), pagination.getPageSize());

        try {
            return jdbcTemplate.query(sql, sqlParm, postRowMapper()); // List<Post> 반환
        } catch (Exception e) {
            log.debug("ERROR : getPostList() query error",e);
        }
        return null;
    }

    @Override
    public void update(Post post) {
        String sql = "UPDATE BOARD " +
                "SET TITLE=:title, WRITER=:writer, CONTENT=:content " +
                "WHERE ID=:id AND DELETEFLAG=FALSE;";
        SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(post);

        jdbcTemplate.update(sql, sqlParam);
    }

    @Override
    public void update(long postId) {
    }

    @Override
    public void delete(Post post) {
    }

    @Override
    public void delete(long postId) {
        String sql = "UPDATE BOARD SET DELETEFLAG=TRUE WHERE ID=:id";

        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("id", postId);

        jdbcTemplate.update(sql, sqlParam);
    }

    @Override
    public void plusHit(long postId){
        String sql = "UPDATE BOARD SET HIT = HIT + 1 " +
                "WHERE ID=:id AND DELETEFLAG=FALSE";

        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("id", postId);

        jdbcTemplate.update(sql, sqlParam);
    }

    private RowMapper<Post> postRowMapper() {
        return BeanPropertyRowMapper.newInstance(Post.class);
    }

    public int getTotalPost() {
        String sql = "SELECT COUNT(*) AS \"totalPost\" FROM BOARD WHERE DELETEFLAG=FALSE";

        int totalPost =
                jdbcTemplate.query(sql,
                        (rs, rowNum)
                                -> Integer.parseInt(rs.getString("totalPost")))
                        .get(0);

        // 마지막 페이지 값 받아오는 쿼리 작성
        return totalPost;
    }
}
