package com.example.repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderRepository {

//    private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
//        Order order = new Order();
//        order.setId(rs.getInt("id"));
//        return order;
//    };

    private static final ResultSetExtractor<List<Order>> ORDER_ROW_MAPPER = (rs) -> {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order();
        List<OrderItem> orderItemList = new ArrayList<>();
        boolean isFirstLine = true;


        while(rs.next()) {
            if(isFirstLine){
                order.setId(rs.getInt("o_id"));
                order.setUserId(rs.getInt("o_user_id"));
                order.setStatus(rs.getInt("o_status"));
                order.setTotalPrice(rs.getInt("o_total_price"));
                order.setOrderDate(rs.getDate("o_order_date"));
                order.setDistationName(rs.getString("o_destination_name"));
                order.setDistationEmail(rs.getString("o_destination_email"));
                order.setDistationZipcode(rs.getString("o_destination_zipcode"));
                order.setDistationPrefecture(rs.getString("o_destination_prefecture"));
                order.setDistationMunicipalities(rs.getString("o_destination_municipalities"));
                order.setDistationAddress(rs.getString("o_destination_address"));
                order.setDistationTel(rs.getString("o_destination_tel"));
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
     * @param id 注文ID
     * @return 注文
     */
    public Order findById(Integer id) {
//            TODO:未実装
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
                WHERE o.id = :id;
                """;
        SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        List<Order> orderList = template.query(sql,param, ORDER_ROW_MAPPER);

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
//        TODO:未実装
    }
}
