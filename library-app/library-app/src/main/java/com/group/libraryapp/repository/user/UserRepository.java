package com.group.libraryapp.repository.user;

import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {   //SQL을 사용해 실제 DB와 통신

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean isUserNotExist(long id) {
        String readSql = "SELECT * FROM user WHERE id = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, id).isEmpty();
        //결과가 있으면 0 반환 -> 해당 값이 있으면 0이 담긴 리스트, 없으면 빈 리스트 반환 (query의 결과가 리스트이기 때문)
    }

    public boolean isUserNotExist(String name) {
        String readSql = "SELECT * FROM user WHERE name = ?";
        return jdbcTemplate.query(readSql, (rs, rowNum) -> 0, name).isEmpty();
        //결과가 있으면 0 반환 -> 해당 값이 있으면 0이 담긴 리스트, 없으면 빈 리스트 반환 (query의 결과가 리스트이기 때문)
    }

    public void saveUser(String name, int age) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, name, age); //위에 ?에 순서대로 각각 데이터가 순서대로(이름, 나이) 매핑된다.
    }

    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {  //jdbcTemplate의 쿼리가 sql을 수행한 결과를 UserResponse로 바꿔주는 역할
            long id = rs.getLong("id"); //rs가 쿼리 결과임
            String name = rs.getString("name");
            int age = rs.getInt("age");
            return new UserResponse(id, name, age);
        });
    }

    public void updateUserName(String name, long id) {
        String sql = "UPDATE user SET name = ? WHERE id = ?";
        jdbcTemplate.update(sql, name, id);
    }

    public void deleteUser(String name) {
        String sql = "DELETE FROM user WHERE name = ?";
        jdbcTemplate.update(sql, name);
    }
}
