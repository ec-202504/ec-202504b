package com.example.domain;

public enum OrderStatus {
    BEFORE_ORDER(0, "注文前"),
    UNPAID(1, "未入金"),
    PAID(2, "入金済"),
    SHIPPED(3, "発送済"),
    DELIVERED(4, "配送完了"),
    CANCELLED(9, "キャンセル");

    private final int code;
    private final String label;

    OrderStatus(int code, String label) {
        this.code = code;
        this.label = label;
    }

    public int getCode() {
        return code;
    }

    public String getLabel() {
        return label;
    }

    /**
     * 数値からEnumを取得
     */
    public static OrderStatus fromCode(int code) {
        for (OrderStatus status : OrderStatus.values()) {
            if (status.code == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("不正なステータスコード: " + code);
    }
}
