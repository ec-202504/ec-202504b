package com.example.form;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderItemForm {
    /**
     * 商品ID(正の整数)
     */
    @NotNull(message = "商品IDは必須です")
    @Positive(message = "商品IDは正の整数で入力してください")
    private Integer itemId;
    /**
     * 数量(必須、1以上)
     */
    @NotNull(message = "数量は必須です")
    @Min(value = 1, message = "数量は1以上で入力してください")
    private Integer quantity;
    /**
     * アイテムのサイズ(任意)
     */
    private Integer optionItmSize;
    /**
     * 靴のサイズ(任意)
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
