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
                SELECT id,name,description,price,imagepath,deleted,itemtype FROM
                 items WHERE deleted = false ORDER BY price
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
                SELECT id,name,description,price,imagepath,deleted,itemtype FROM
                 items WHERE name LIKE :name ORDER BY price
                """;

        SqlParameterSource param =
                new MapSqlParameterSource().addValue("name", "%" + name + "%");

        return template.query(sql, param, ITEM_ROW_MAPPER);
    }

    /**
     * 主キーで商品を検索します.
     *
     * @param id itmesテーブルの主キーである商品ID
     * @return 商品情報
     */
    public Item findById(Integer id) {
        String sql = """
                SELECT id,name,description,price,imagepath,deleted,itemtype FROM
                 items WHERE id=:id
                """;

        SqlParameterSource param
                = new MapSqlParameterSource().addValue("id", id);

        return template.queryForObject(sql, param, ITEM_ROW_MAPPER);
    }

    /**
     * おすすめ商品のリストを取得します.
     *
     * @param infant   乳児向けかどうか
     * @param senior   高齢者向けかどうか
     * @param gender   女性向けかどうか
     * @param pet      ペットがいるかどうか
     * @param disaster 災害種別
     * @return 条件に合致する商品一覧
     */
    public List<Item> recommend(String infant, String senior, String gender,
                                Boolean pet, String disaster) {
        String sql = """
                 SELECT id,name,description,price,imagepath,deleted,itemtype FROM
                 items WHERE id IN
                 (SELECT DISTINCT COALESCE(g.item_id,a.item_id,p.item_id,d.item_id) FROM genders g
                 FULL OUTER JOIN ageGroups a ON g.item_id = a.item_id
                 FULL OUTER JOIN pets p ON g.item_id = p.item_id
                 FULL OUTER JOIN disasters d ON g.item_id = d.item_id
                 WHERE g.gender=:gender OR a.age=:infant
                 OR a.age=:senior OR p.pet=:pet OR d.disaster=:disaster)
                 ORDER BY price
                """;

        SqlParameterSource param
                = new MapSqlParameterSource()
                .addValue("gender", gender)
                .addValue("infant", infant)
                .addValue("senior", senior)
                .addValue("pet", pet)
                .addValue("disaster", disaster);

        return template.query(sql, param, ITEM_ROW_MAPPER);
    }
}
