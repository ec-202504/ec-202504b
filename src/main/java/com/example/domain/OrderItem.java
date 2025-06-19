package com.example.domain;

/**
 * 注文商品情報を表すエンティティ。
 * <p>注文ごとの商品や数量、オプション情報などを保持します。</p>
 */
public class OrderItem {
    /** 注文商品ID */
    private Integer id;
    /** 商品ID */
    private Integer itemId;
    /** 注文ID */
    private Integer orderId;
    /** 数量 */
    private Integer quantity;
    /** サイズ */
    private Character size;
    /** 商品情報 */
    private Item item;
    /** アイテムのサイズ */
    private Integer optionItemSize;
    /** 靴のサイズ */
    private Integer optionShoesSize;
   

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getItemId() { return itemId; }
    public void setItemId(Integer itemId) { this.itemId = itemId; }
    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    public Character getSize() { return size; }
    public void setSize(Character size) { this.size = size; }
    public Item getItem() { return item; }
    public void setItem(Item item) { this.item = item; }
    public Integer getOptionItemSize() { return optionItemSize; }
    public void setOptionItemSize(Integer optionItemSize) { this.optionItemSize = optionItemSize; }
    public Integer getOptionShoesSize() { return optionShoesSize; }
    public void setOptionShoesSize(Integer optionShoesSize) { this.optionShoesSize = optionShoesSize; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", size=" + size +
                ", item=" + item +
                ", optionItemSize=" + optionItemSize +
                ", optionShoesSize=" + optionShoesSize +
                '}';
    }
} 