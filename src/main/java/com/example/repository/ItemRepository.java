package com.example.repository;

import com.example.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * itemsテーブルを操作するリポジトリクラス.
 */
@Repository
public class ItemRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private static final RowMapper<Item> ITEM_ROW_MAPPER
            = (rs, i) -> {
        Item item = new Item();
        item.setId(rs.getInt("id"));
        item.setName(rs.getString("name"));
        item.setDescription(rs.getString("description"));
        item.setPrice(rs.getInt("price"));
        item.setImagePath(rs.getString("imagepath"));
        item.setDeleted(rs.getBoolean("deleted"));
        item.setItemType(rs.getBoolean("itemtype"));
        return item;
    };

    /**
     * 全件検索します.
     *
     * @return 商品一覧
     */
    public List<Item> findAll() {
        String sql = """
                SELECT * FROM items WHERE deleted = false ORDER BY price
                """;

        return template.query(sql, ITEM_ROW_MAPPER);
    }

    /**
     * 商品を名前で部分検索します.
     *
     * @param name 検索文字列
     * @return 条件に合致する商品一覧
     */
    public List<Item> findByName(String name) {
        String sql = """
                SELECT * FROM items WHERE name LIKE :name ORDER BY price
                """;

        SqlParameterSource param =
                new MapSqlParameterSource().addValue("name", "%" + name + "%");

        return template.query(sql, param, ITEM_ROW_MAPPER);
    }
}
