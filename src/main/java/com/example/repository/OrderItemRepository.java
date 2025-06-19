package com.example.repository;

import com.example.domain.OrderItem;
/**
 * 注文商品情報を管理するリポジトリクラス。
 */
public class OrderItemRepository {

    /**
     * 注文商品に基づいて注文商品を検索します。
     * @param orderItem 注文商品オブジェクト
     * @return 該当する注文商品オブジェクト、存在しない場合はnull
     */
    public void insert(OrderItem orderItem) {
        // TODO: 注文商品をデータベースに挿入する実装に修正
    }

    /**
     * 注文商品IDに基づいて注文商品を削除します。
     * @param orderItemId 注文商品ID
     */
    public void deleteByOrderItemId(Integer orderItemId) {
        // TODO: 注文商品IDに基づいて注文商品を削除する実装に修正
    }


}
