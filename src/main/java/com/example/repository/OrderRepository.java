package com.example.repository;

import com.example.domain.Order;
import com.example.domain.OrderItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

/**
 * 注文情報を管理するリポジトリクラス。
 */
public class OrderRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // OrderのRowMapper
    private static final RowMapper<Order> ORDER_ROW_MAPPER = new RowMapper<Order>() {
        @Override
        public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("user_id"));
            order.setStatus(rs.getInt("status"));
            order.setTotalPrice(rs.getInt("total_price"));
            order.setOrderDate(rs.getDate("order_date"));
            order.setDestinationName(rs.getString("destination_name"));
            order.setDestinationEmail(rs.getString("destination_email"));
            order.setDestinationZipcode(rs.getString("destination_zipcode"));
            order.setDestinationPrefecture(rs.getString("destination_prefecture"));
            order.setDestinationMunicipalities(rs.getString("destination_municipalities"));
            order.setDestinationAddress(rs.getString("destination_address"));
            order.setDestinationTel(rs.getString("destination_tel"));
            order.setDeliveryTime(rs.getTimestamp("delivery_time"));
            order.setPaymentMethod(rs.getInt("payment_method"));
            // orderItemListは後でセット
            return order;
        }
    };

    /**
     * OrderItemテーブルの1行をOrderItemオブジェクトに変換するRowMapper
     */
    private static final RowMapper<OrderItem> ORDER_ITEM_ROW_MAPPER = new RowMapper<OrderItem>() {
        @Override
        public OrderItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setItemId(rs.getInt("item_id"));
            item.setOrderId(rs.getInt("order_id"));
            item.setQuantity(rs.getInt("quantity"));
            // shoes_sizeはCHAR(1)型なので、nullでなければ1文字目をセット
            String shoesSize = rs.getString("shoes_size");
            if (shoesSize != null && !shoesSize.isEmpty()) {
                // 必要に応じて型変換（例：M→77など）
                item.setOptionShoesSize((int) shoesSize.charAt(0));
            }
            return item;
        }
    };

    /**
     * ユーザーIDとステータスに基づいて注文を検索します.
     * @param userId ユーザーID
     * @param status 注文ステータス
     * @return 該当する注文オブジェクト、存在しない場合はnull
     */
    public Order findByUserIdAndStatus(Integer userId, Integer status) {
        // 注文情報を取得するSQL文
        String sql = "SELECT * FROM orders WHERE user_id = ? AND status = ?";
        // 注文情報を取得する
        List<Order> orderList = jdbcTemplate.query(sql, ORDER_ROW_MAPPER, userId, status);
        // 注文情報が存在しない場合はnullを返す
        if(orderList.isEmpty()){
            return null;
        }
        // 注文情報が存在する場合、最初の注文を返す
        Order order = orderList.get(0);

        //orderItemListを取得するSQL文
        String itemsql = "SELECT * FROM order_items WHERE order_id = ?";
        //orderItemListを取得する
        List<OrderItem> orderItemList = jdbcTemplate.query(itemsql, ORDER_ITEM_ROW_MAPPER, order.getId());
        // 注文オブジェクトに注文商品リストをセット
        order.setOrderItemList(orderItemList);
        // 注文オブジェクトを返す
        return order;
        
    }

    /**
     * 注文をデータベースに挿入します.
     * @param order 挿入する注文オブジェクト
     */
    public void insert(Order order) {
        // 注文を挿入するSQL文
        String sql = "INSERT INTO orders (user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_prefecture, destination_municipalities, destination_address, destination_tel, delivery_time, payment_method) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // 自動採番されたIDを取得するためのKeyHolder
        KeyHolder keyHolder = new GeneratedKeyHolder();
        // jdbcTemplateを使用して注文を挿入
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, order.getUserId());
            ps.setInt(2, order.getStatus());
            ps.setObject(3, order.getTotalPrice(), Types.INTEGER);
            ps.setObject(4, order.getOrderDate(), Types.DATE);
            ps.setString(5, order.getDestinationName());
            ps.setString(6, order.getDestinationEmail());
            ps.setString(7, order.getDestinationZipcode());
            ps.setString(8, order.getDestinationPrefecture());
            ps.setString(9, order.getDestinationMunicipalities());
            ps.setString(10, order.getDestinationAddress());
            ps.setString(11, order.getDestinationTel());
            ps.setObject(12, order.getDeliveryTime(), Types.TIMESTAMP);
            ps.setObject(13, order.getPaymentMethod(), Types.INTEGER);
            return ps;
        }, keyHolder);

        // 自動採番されたIDをOrderオブジェクトにセット
        Number key = keyHolder.getKey();
        // keyがnullでない場合のみIDをセット
        if (key != null) {
            order.setId(key.intValue());
        }

    }
}
