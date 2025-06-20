package com.example.repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.*;
import java.util.List;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import com.example.domain.Item;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 注文情報を管理するリポジトリクラス。
 */
@Repository
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
                item.setShoesSize(shoesSize.charAt(0));
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

        //orderItemListを取得するSQL文（itemsテーブルとJOINしてitem情報も取得）
        String itemsql = "SELECT oi.*, i.name AS item_name, i.description AS item_description, i.price AS item_price, i.imagepath AS item_image_path " +
                "FROM order_items oi INNER JOIN items i ON oi.item_id = i.id WHERE oi.order_id = ?";
        //orderItemListを取得する
        List<OrderItem> orderItemList = jdbcTemplate.query(itemsql, (rs, rowNum) -> {
            OrderItem item = new OrderItem();
            item.setId(rs.getInt("id"));
            item.setItemId(rs.getInt("item_id"));
            item.setOrderId(rs.getInt("order_id"));
            item.setQuantity(rs.getInt("quantity"));
            String shoesSize = rs.getString("shoes_size");
            if (shoesSize != null && !shoesSize.isEmpty()) {
                item.setShoesSize(shoesSize.charAt(0));
            }
            // Item情報もセット
            com.example.domain.Item itemObj = new com.example.domain.Item();
            itemObj.setId(rs.getInt("item_id"));
            itemObj.setName(rs.getString("item_name"));
            itemObj.setDescription(rs.getString("item_description"));
            itemObj.setPrice(rs.getInt("item_price"));
            itemObj.setImagePath(rs.getString("item_image_path"));
            item.setItem(itemObj);
            return item;
        }, order.getId());
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
            PreparedStatement ps = con.prepareStatement(sql, new String[]{"id"});
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

//@Repository
//public class OrderRepository {

//    private static final RowMapper<Order> ORDER_RESULT_ROW_MAPPER = (rs, i) -> {
//        Order order = new Order();
//        order.setId(rs.getInt("id"));
//        return order;
//    };

    private static final ResultSetExtractor<List<Order>> ORDER_RESULT_ROW_MAPPER = (rs) -> {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        boolean isFirstLine = true;


        while (rs.next()) {
            if (isFirstLine) {
                order.setId(rs.getInt("o_id"));
                order.setUserId(rs.getInt("o_user_id"));
                order.setStatus(rs.getInt("o_status"));
                order.setTotalPrice(rs.getInt("o_total_price"));
                order.setOrderDate(rs.getDate("o_order_date"));
                order.setDestinationName(rs.getString("o_destination_name"));
                order.setDestinationEmail(rs.getString("o_destination_email"));
                order.setDestinationZipcode(rs.getString("o_destination_zipcode"));
                order.setDestinationPrefecture(rs.getString("o_destination_prefecture"));
                order.setDestinationMunicipalities(rs.getString("o_destination_municipalities"));
                order.setDestinationAddress(rs.getString("o_destination_address"));
                order.setDestinationTel(rs.getString("o_destination_tel"));
                order.setDeliveryTime(rs.getTimestamp("o_delivery_time")); // TIMESTAMP型の場合
                order.setPaymentMethod(rs.getInt("o_payment_method"));
                order.setOrderItemList(orderItemList);
                isFirstLine = false;
            }

            OrderItem orderItem = new OrderItem();
            orderItem.setId(rs.getInt("oi_id"));
            orderItem.setItemId(rs.getInt("oi_item_id"));
            orderItem.setOrderId(rs.getInt("oi_order_id"));
            orderItem.setQuantity(rs.getInt("oi_quantity"));
//            orderItem.setSize(rs.getString("oi_shoes_size")); TODO:直す

            Item item = new Item();
            item.setId(rs.getInt("oi_item_id"));
            item.setName(rs.getString("i_name"));
            item.setDescription(rs.getString("i_description"));
            item.setPrice(rs.getInt("i_price"));
            item.setImagePath(rs.getString("i_image_path"));
            orderItem.setItem(item);
            orderItemList.add(orderItem);
        }

        orderList.add(order);
        return orderList;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * status=0(注文前)の注文を検索する.
     *
     * @param userId 注文ID
     * @return 注文
     */
    public Order findByUserIdAndStatus0(Integer userId) {
        final String sql = """
                SELECT
                o.id AS o_id,
                o.user_id AS o_user_id,
                o.status AS o_status,
                o.total_price AS o_total_price,
                o.order_date AS o_order_date,
                o.destination_name AS o_destination_name,
                o.destination_email AS o_destination_email,
                o.destination_zipcode AS o_destination_zipcode,    
                o.destination_prefecture AS o_destination_prefecture,  
                o.destination_municipalities AS o_destination_municipalities, 
                o.destination_address AS o_destination_address,   
                o.destination_tel AS o_destination_tel,
                o.delivery_time AS o_delivery_time,
                o.payment_method AS o_payment_method,
                oi.id AS oi_id,
                oi.item_id AS oi_item_id,
                oi.order_id AS oi_order_id,
                oi.quantity AS oi_quantity,
                oi.shoes_size AS oi_shoes_size,
                i.name AS i_name,
                i.description AS i_description,
                i.price AS i_price,
                i.imagepath AS i_image_path
                FROM orders AS o
                INNER JOIN order_items AS oi
                ON o.id = oi.order_id
                INNER JOIN items AS i
                ON oi.item_id = i.id
                WHERE o.id = :userId AND o.status=0;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
        List<Order> orderList = template.query(sql, param, ORDER_RESULT_ROW_MAPPER);

        if (orderList.size() == 0)
            return null;

        return orderList.get(0);
    }

    /**
     * テーブルを更新する
     *
     * @param order 注文
     */
    public void update(Order order) {
        final String sql = """
                UPDATE orders SET
                user_id = :userId,
                status = :status,
                total_price = :totalPrice, 
                order_date = :orderDate,           
                destination_name = :distationName,
                destination_email = :distationEmail,          
                destination_zipcode = :distationZipcode,       
                destination_prefecture = :distationPrefecture,     
                destination_municipalities = :distationMunicipalities,   
                destination_address = :distationAddress,       
                destination_tel = :distationTel,         
                delivery_time = :deliveryTime,                
                payment_method = :paymentMethod
                WHERE id = :id;
                """;
        SqlParameterSource param = new BeanPropertySqlParameterSource(order);
        template.update(sql, param);
    }

    /**
     * 注文履歴を取得します.
     *
     * @param userId ユーザーID
     * @return 注文履歴
     */
    public List<Order> findByUserId(Integer userId) {
        String sql = """
                SELECT
                o.id AS o_id,
                o.user_id AS o_user_id,
                o.status AS o_status,
                o.total_price AS o_total_price,
                o.order_date AS o_order_date,
                o.destination_name AS o_destination_name,
                o.destination_email AS o_destination_email,
                o.destination_zipcode AS o_destination_zipcode,    
                o.destination_prefecture AS o_destination_prefecture,  
                o.destination_municipalities AS o_destination_municipalities, 
                o.destination_address AS o_destination_address,   
                o.destination_tel AS o_destination_tel,
                o.delivery_time AS o_delivery_time,
                o.payment_method AS o_payment_method,
                oi.id AS oi_id,
                oi.item_id AS oi_item_id,
                oi.order_id AS oi_order_id,
                oi.quantity AS oi_quantity,
                oi.shoes_size AS oi_shoes_size,
                i.name AS i_name,
                i.description AS i_description,
                i.price AS i_price,
                i.imagepath AS i_image_path
                FROM orders AS o
                INNER JOIN order_items AS oi
                ON o.id = oi.order_id
                INNER JOIN items AS i
                ON oi.item_id = i.id
                WHERE o.user_id = :userId;
                """;

        SqlParameterSource param
                = new MapSqlParameterSource().addValue("userId", userId);

        List<Order> orderList = template.query(sql, param, ORDER_RESULT_ROW_MAPPER);

        if (orderList.size() == 0)
            return null;

        return orderList;
    }
}
