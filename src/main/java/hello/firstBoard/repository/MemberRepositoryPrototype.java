package hello.firstBoard.repository;

import hello.firstBoard.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;
import java.util.Optional;

@Repository
@Slf4j
public class MemberRepositoryPrototype implements MemberRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public MemberRepositoryPrototype(DataSource dataSource) {
        // dataSource에서 autowired안된다고 에러나는건 intellij 버그임
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        log.info("dataSource = {}", dataSource);
        try {
            log.info("dataSource.getConnection() = {}", dataSource.getConnection());
        } catch (SQLException e) {
            log.info("ERROR OCCURED ; datasource DI error ");
        }
    }

    @Override
    public Member save(Member member) {

        String sql = "INSERT INTO MEMBER(USERID, USERPASSWORD, NAME, AGE) "
                + "VALUES(:userId, :userPassword, :name, :age)";
        SqlParameterSource sqlParam = new BeanPropertySqlParameterSource(member);
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, sqlParam, keyHolder);

        Long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public Optional<Member> findByUserId(String userId) {

        String sql = "SELECT * FROM MEMBER WHERE USERID = :userId";
        MapSqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("userId", userId);
        try {
            Member member = jdbcTemplate.queryForObject(sql, sqlParam, memberRowMapper());
            return Optional.of(member);
        } catch (EmptyResultDataAccessException e) {
            log.info("ERROR : {} ; tried to find {}, but not found at finByUserId", e, userId);
            return Optional.empty();
        }

    }

    @Override
    public List<Member> findALL() {
        String sql = "SELECT * FROM MEMBER";
        List<Member> result = jdbcTemplate.query(sql, memberRowMapper());
        return result;
    }

    @Override
    public boolean updatePassword(Member member, String userPassword) {
        String sql = "UPDATE MEMBER SET USERPASSWORD=:userPassword WHERE USERID=:userId";
        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("userPassword", userPassword)
                .addValue("userId", member.getUserId());

        int rows = jdbcTemplate.update(sql, sqlParam);

        return false;
    }

    @Override
    public boolean deleteUser(Member member) {
        // DELETE FROM TEST WHERE USERID = 'TEST1';
        String sql = "DELETE FROM MEMBER WHERE USERID = :userId";
        SqlParameterSource sqlParam = new MapSqlParameterSource()
                .addValue("userId", member.getUserId());

        int update = jdbcTemplate.update(sql, sqlParam);
        log.info("deleted {} rows FROM MEMBER", update);

        return true;
    }

    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }
}
