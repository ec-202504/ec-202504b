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
    /** オプション1 */
    private Integer option1;
    /** オプション2 */
    private Integer option2;
    /** オプション3 */
    private Integer option3;

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
    public Integer getOption1() { return option1; }
    public void setOption1(Integer option1) { this.option1 = option1; }
    public Integer getOption2() { return option2; }
    public void setOption2(Integer option2) { this.option2 = option2; }
    public Integer getOption3() { return option3; }
    public void setOption3(Integer option3) { this.option3 = option3; }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", itemId=" + itemId +
                ", orderId=" + orderId +
                ", quantity=" + quantity +
                ", size=" + size +
                ", item=" + item +
                ", option1=" + option1 +
                ", option2=" + option2 +
                ", option3=" + option3 +
                '}';
    }
} 