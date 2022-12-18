package hello.firstBoard.repository;

import hello.firstBoard.domain.board.Pages.PageDAO;
import hello.firstBoard.domain.board.Pages.Search;
import hello.firstBoard.domain.board.Pages.SearchDAO;
import hello.firstBoard.domain.board.Pages.SearchType;
import hello.firstBoard.domain.board.Posts.Post;
import hello.firstBoard.utils.SQLDateUtils;

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

        // -- sql이 string으로 던져주는 date값을
        // post에 있는 LocalDateTime date 필드에 파싱 시켜주기
        String sqlDateString = keys.get("DATE").toString();
        post.setDate(SQLDateUtils.SQLDateStrToLocalDateTime(sqlDateString));

        return post;
    }

    @Override
    public Post getPost(long postId) {
        String sql = "SELECT * FROM BOARD " +
                "WHERE ID=:postId AND DELETEFLAG=FALSE";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("postId", postId);

        return jdbcTemplate.queryForObject(sql, param, postRowMapper());
    }

/*
    @Deprecated
    public List<Post> getPostList(Page pagination) {
        String sql = "SELECT ID, TITLE, WRITER, DATE, HIT FROM BOARD " +
                "WHERE DELETEFLAG=FALSE " +
                "ORDER BY ID DESC LIMIT :start, :size";
        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("start", pagination.getStartPost())
                .addValue("size", pagination.getPageSize());

        log.debug("start : {} , size : {} ", pagination.getStartPost(), pagination.getPageSize());
        try {
            return jdbcTemplate.query(sql, sqlParam, postRowMapper()); // List<Post> 반환
        } catch (Exception e) {
            log.debug("ERROR : getPostList() query error",e);
        }
        return null;
    }
*/

    @Override
    public List<Post> getPostList(PageDAO pageDAO) {
        String sql = "SELECT ID, TITLE, WRITER, DATE, HIT FROM BOARD " +
                "WHERE DELETEFLAG=FALSE " +
                "ORDER BY ID DESC LIMIT :offsetPostCount, :pageSize";
        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("offsetPostCount", pageDAO.getOffsetPostCount())
                .addValue("pageSize", pageDAO.getPageSize());

        try {
            return jdbcTemplate.query(sql, sqlParam, postRowMapper()); // List<Post> 반환
        } catch (Exception e) {
            log.warn("ERROR : getPostList() query error ", e);
        }
        return null;
    }

    @Override
    public List<Post> getPostSearchList(Search search, PageDAO pageDAO) {
        // 예시 ; 전체 쿼리
        /* 동적 쿼리 처리가 필요함
        ex.
         SELECT * FROM BOARD WHERE DELETEFLAG=FALSE AND
            TITLE LIKE CONCAT('%',':searchKeyword','%')
            ORDER BY ID DESC LIMIT :start, :size ;
         */

        // (a) 앞쿼리
        StringBuilder sql = new StringBuilder()
                .append("SELECT ID, TITLE, WRITER, DATE, HIT FROM BOARD WHERE DELETEFLAG=FALSE AND ");

        // (b) 동적쿼리
        // 예시 ; 동적 쿼리 부분
        /*
        SELECT * FROM BOARD
          WHERE DELETEFLAG=FALSE AND
          ( TITLE LIKE CONCAT('%', '테스트', '%') OR CONTENT LIKE CONCAT('%', '테스트', '%') )
          ORDER BY ID DESC LIMIT 5
         */
        searchTypeDynamicQuery(search.getSearchType(), sql);

        // (c) id순 정렬 쿼리
        sql.append("ORDER BY ID DESC LIMIT :start, :size ;");
        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("searchKeyword", search.getSearchKeyword())
                .addValue("start", pageDAO.getOffsetPostCount())
                .addValue("size", pageDAO.getPageSize());

        return jdbcTemplate.query(sql.toString(), sqlParam, postRowMapper()); // List<Post> 반환

    }

    @Override
    public int getSearchTotalPost(Search search) {
        /*
        예시 쿼리
        SELECT COUNT(*) FROM BOARD
          WHERE DELETEFLAG=FALSE
          AND ( TITLE LIKE CONCAT('%', '테스트', '%') OR CONTENT LIKE CONCAT('%', '테스트', '%') )
         */
        StringBuilder sql = new StringBuilder()
                .append("SELECT COUNT(*) FROM BOARD WHERE DELETEFLAG=FALSE AND ");
        searchTypeDynamicQuery(search.getSearchType(), sql);

        SqlParameterSource sqlParm = new MapSqlParameterSource()
                .addValue("searchKeyword", search.getSearchKeyword());

        int totalPost = jdbcTemplate.queryForObject(sql.toString(), sqlParm, Integer.class);
        log.info("repository 에서 searchTotalPost : {}",totalPost);
        return totalPost;
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

    private void searchTypeDynamicQuery(SearchType searchType, StringBuilder stringBuilder) {
        switch (searchType) {
            case TITLE:
                stringBuilder.append("TITLE LIKE CONCAT('%',:searchKeyword,'%') ");
                break;
            case WRITER:
                stringBuilder.append("WRITER LIKE CONCAT('%',:searchKeyword,'%') ");
                break;
            case CONTENT:
                stringBuilder.append("CONTENT LIKE CONCAT('%',:searchKeyword,'%') ");
                break;
            case TITLEORCONTENT:
                stringBuilder.append("(TITLE LIKE CONCAT('%', :searchKeyword, '%') OR CONTENT LIKE CONCAT('%', :searchKeyword, '%')) ");
                break;
            default:
                log.info("SearchType 값이 이상합니다!! :: " + searchType);
                break;
        }
    }
}
