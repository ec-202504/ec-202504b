package com.example.domain;

import java.util.Date;
import java.util.List;

/**
 * 注文情報を表すエンティティ。
 * <p>ユーザーの注文内容や配送先、支払い方法などを保持します。</p>
 */
public class Order {
    /** 注文ID */
    private Integer id;
    /** ユーザーID */
    private Integer userId;
    /** ステータス */
    private Integer status;
    /** 合計金額 */
    private Integer totalPrice;
    /** 注文日時 */
    private Date orderDate;
    /** 配送先氏名 */
    private String destinationName;
    /** 配送先メール */
    private String destinationEmail;
    /** 配送先郵便番号 */
    private String destinationZipcode;
    /** 配送先都道府県 */
    private String destinationPrefecture;
    /** 配送先市区町村 */
    private String destinationMunicipalities;
    /** 配送先住所 */
    private String destinationAddress;
    /** 配送先電話番号 */
    private String destinationTel;
    /** 配送希望日時 */
    private Date deliveryTime;
    /** 支払い方法 */
    private Integer paymentMethod;
    /** 注文商品リスト */
    private List<OrderItem> orderItemList;

    /**
     * この注文の合計金額にかかる消費税を計算する.
     *
     * @return カートの商品の消費税の金額を返す
     */
    public int getTax(){
        int sum = 0;
        for(OrderItem orderItem: orderItemList){
            sum += orderItem.getQuantity() * orderItem.getItem().getPrice();
        }

        int tax = sum/10; //合計金額の税率10%
        return tax;
    }

    /**
     * この注文の消費税込みの合計金額を計算する.
     *
     * @return 合計金額
     */
    public int getCalcTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem: orderItemList){
            totalPrice += orderItem.getQuantity() * orderItem.getItem().getPrice();
        }
        totalPrice += this.getTax(); //消費税分を加算する
        return totalPrice;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }
    public Integer getStatus() { return status; }
    public void setStatus(Integer status) { this.status = status; }
    public Integer getTotalPrice() { return totalPrice; }
    public void setTotalPrice(Integer totalPrice) { this.totalPrice = totalPrice; }
    public java.util.Date getOrderDate() { return orderDate; }
    public void setOrderDate(java.util.Date orderDate) { this.orderDate = orderDate; }
    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }
    public String getDestinationEmail() { return destinationEmail; }
    public void setDestinationEmail(String destinationEmail) { this.destinationEmail = destinationEmail; }
    public String getDestinationZipcode() { return destinationZipcode; }
    public void setDestinationZipcode(String destinationZipcode) { this.destinationZipcode = destinationZipcode; }
    public String getDestinationPrefecture() { return destinationPrefecture; }
    public void setDestinationPrefecture(String destinationPrefecture) { this.destinationPrefecture = destinationPrefecture; }
    public String getDestinationMunicipalities() { return destinationMunicipalities; }
    public void setDestinationMunicipalities(String destinationMunicipalities) { this.destinationMunicipalities = destinationMunicipalities; }
    public String getDestinationAddress() { return destinationAddress; }
    public void setDestinationAddress(String destinationAddress) { this.destinationAddress = destinationAddress; }
    public String getDestinationTel() { return destinationTel; }
    public void setDestinationTel(String destinationTel) { this.destinationTel = destinationTel; }
    public java.util.Date getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(java.util.Date deliveryTime) { this.deliveryTime = deliveryTime; }
    public Integer getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Integer paymentMethod) { this.paymentMethod = paymentMethod; }
    public java.util.List<OrderItem> getOrderItemList() { return orderItemList; }
    public void setOrderItemList(java.util.List<OrderItem> orderItemList) { this.orderItemList = orderItemList; }

    /**
     * 消費税を計算して返す（10%）
     */
    public int getTax() {
        if (orderItemList == null) return 0;
        int sum = 0;
        for (OrderItem orderItem : orderItemList) {
            sum += orderItem.getCalcSubTotalPrice();
        }
        return (int)(sum * 0.1);
    }

    /**
     * 合計金額（税込）を計算して返す
     */
    public int getCalcTotalPrice() {
        if (orderItemList == null) return 0;
        int sum = 0;
        for (OrderItem orderItem : orderItemList) {
            sum += orderItem.getCalcSubTotalPrice();
        }
        return sum + getTax();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", destinationName='" + destinationName + '\'' +
                ", destinationEmail='" + destinationEmail + '\'' +
                ", destinationZipcode='" + destinationZipcode + '\'' +
                ", destinationPrefecture='" + destinationPrefecture + '\'' +
                ", destinationMunicipalities='" + destinationMunicipalities + '\'' +
                ", destinationAddress='" + destinationAddress + '\'' +
                ", destinationTel='" + destinationTel + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", paymentMethod=" + paymentMethod +
                ", orderItemList=" + orderItemList +
                '}';
    }
} 