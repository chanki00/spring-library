package com.group.libraryapp.controller.user;

import com.group.libraryapp.domain.user.Fruit;
import com.group.libraryapp.domain.user.User;
import com.group.libraryapp.dto.user.request.UserCreateRequest;
import com.group.libraryapp.dto.user.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    private final JdbcTemplate jdbcTemplate;

    public UserController(JdbcTemplate jdbcTemplate) {  //의존성 주입
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/user") // POST /user
    public void saveUser(@RequestBody UserCreateRequest request) {
        String sql = "INSERT INTO user (name, age) VALUES (?, ?)";
        jdbcTemplate.update(sql, request.getName(), request.getAge()); //위에 ?에 순서대로 각각 데이터가 순서대로(이름, 나이) 매핑된다.
    }

    @GetMapping("/user")
    public List<UserResponse> getUsers() {
        String sql = "SELECT * FROM user";
        return jdbcTemplate.query(sql, new RowMapper<UserResponse>() {
            @Override
            public UserResponse mapRow(ResultSet rs, int rowNum) throws SQLException {  //jdbcTemplate의 쿼리가 sql을 수행한 결과를 UserResponse로 바꿔주는 역할
                long id = rs.getLong("id"); //rs가 쿼리 결과임
                String name = rs.getString("name");
                int age = rs.getInt("age");
                return new UserResponse(id, name, age);
            }
        });
    }


//    @GetMapping("/fruit")
//    public Fruit fruit() {
//        return new Fruit("바나나", 2000);
//    }

}
