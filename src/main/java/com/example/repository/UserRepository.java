package com.example.repository;

import com.example.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * ユーザーを登録する.
     *
     * @param user ユーザー
     */
    public void insert(User user){
        SqlParameterSource param = new BeanPropertySqlParameterSource(user);
        String sql = """
                INSERT INTO users (name, email, password, zipcode, prefecture, municipalities, address, telephone) VALUES (:name, :email, :password, :zipcode, :prefecture, :municipalities, :address, :telephone);
                """;
        template.update(sql, param);
    }

    /**
     * メールアドレスとパスワードでユーザーを検索する.
     *
     * @param email メールアドレス
     * @param password パスワード
     * @return ユーザー
     */
    public User findByEmailAndPassword(String email, String password){
        String sql = """
				SELECT name, email, password, zipcode, prefecture, municipalities, address, telephone FROM users where email = :email and password = :password;
				""";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
        List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
        if (userList.isEmpty()) {
            return null;
        }
        return userList.get(0);
    }


    /**
     * メールアドレスからユーザーを取得
     *
     * @param email メールアドレス
     * @return ユーザー 存在しない場合はnull
     */
    public User findByMailAddress(String email) {
        String sql =  """
				SELECT name, email, password, zipcode, prefecture, municipalities, address, telephone FROM users where email = :email;
				""";
        SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
        List<User> userList = template.query(sql, param, USER_ROW_MAPPER);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }


}
