package com.example.repository;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        return order;
    };

    @Autowired
    private NamedParameterJdbcTemplate template;

    /**
     * @param id 注文ID
     * @return 注文
     */
    public Order findById(Integer id) {
//            TODO:未実装
        return new Order();
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
