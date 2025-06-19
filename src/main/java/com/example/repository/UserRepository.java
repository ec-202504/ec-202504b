package com.example.repository;

import com.example.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

/**
 * ユーザー情報を操作するリポジトリ.
 */
@Repository
public class UserRepository {
    private static final RowMapper<User> USER_ROW_MAPPER
            = (rs, i) -> {
        User user = new User();
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPassword(rs.getString("password"));
        user.setZipcode(rs.getString("zipcode"));
        user.setPrefecture(rs.getString("prefecture"));
        user.setMunicipalities(rs.getString("municipalities"));
        user.setAddress(rs.getString("address"));
        user.setTelephone(rs.getString("telephone"));
        return user;
    };

    private NamedParameterJdbcTemplate template;

    public void insert(User user){
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        String sql = """
                INSERT INTO users (name, email, password, zipcode, prefecture, municipalities, address, telephone) VALUES (:name, :email, :password, :zipcode, :prefecture, :municipalities, :address, :telephone);
                """;
        template.update(sql, param);
    }


}
