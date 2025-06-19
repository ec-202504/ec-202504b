package com.example.form;

public class OrderItemForm {
    /**
     * Item ID
     */
    private Integer itemId;
    /**
     * 数量
     */
    private Integer quantity;
    /**
     * アイテムのサイズ
     */
    private Integer optionItmSize;
    /**
     * 靴のサイズ
     */
    private Integer optionShoesSize;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getOptionItmSize() {
        return optionItmSize;
    }

    public void setOptionItmSize(Integer optionItmSize) {
        this.optionItmSize = optionItmSize;
    }

    public Integer getOptionShoesSize() {
        return optionShoesSize;
    }

    public void setOptionShoesSize(Integer optionShoesSize) {
        this.optionShoesSize = optionShoesSize;
    }

    /**
     * このオブジェクトの文字列表現を返します。
     * @return オブジェクトの文字列表現
     */
    @Override
    public String toString() {
        return "OrderItemForm{" +
                "itemId=" + itemId +
                ", quantity=" + quantity +
                ", optionItmSize=" + optionItmSize +
                ", optionShoesSize=" + optionShoesSize +
                '}';
    }
}
