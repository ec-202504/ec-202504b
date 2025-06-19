package com.example.repository;

import com.example.domain.Order;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    /**
     *
     * @param id 注文ID
     * @return 注文
     */
         public Order findById(Integer id){
//            TODO:未実装
            return  new Order();
        }
}
