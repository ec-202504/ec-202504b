package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderItem;
/**
 * 注文商品情報を管理するリポジトリクラス。
 */
@Repository
public class OrderItemRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 注文商品に基づいて注文商品を検索します.
     * @param orderItem 注文商品オブジェクト
     * @return 該当する注文商品オブジェクト、存在しない場合はnull
     */
    public void insert(OrderItem orderItem) {
        // 注文商品を追加するSQL文
        String sql = "INSERT INTO order_items (item_id, order_id, quantity, shoes_size) VALUES (?, ?, ?, ?)";
        // shoes_sizeはCHAR(1)型なので、nullであればnullをセット
        String shoesSize = null;
        // オプションの靴のサイズが指定されている場合は、1文字目をセット
        if (orderItem.getOptionShoesSize() != null) {
            shoesSize = String.valueOf((char) orderItem.getOptionShoesSize().intValue());
        }
        // 注文商品をデータベースに挿入
        jdbcTemplate.update(
            sql,
            orderItem.getItemId(),
            orderItem.getOrderId(),
            orderItem.getQuantity(),
            shoesSize
        );
    }

    /**
     * 注文商品IDに基づいて注文商品を削除します.
     * @param orderItemId 注文商品ID
     */
    public void deleteByOrderItemId(Integer orderItemId) {
        // 注文商品を削除するSQL文
        String sql = "DELETE FROM order_items WHERE id = ?";
        // 注文商品をデータベースから削除
        jdbcTemplate.update(sql, orderItemId);
    }
}
