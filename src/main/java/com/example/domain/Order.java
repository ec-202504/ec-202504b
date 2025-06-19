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
    private String distationName;
    /** 配送先メール */
    private String distationEmail;
    /** 配送先郵便番号 */
    private String distationZipcode;
    /** 配送先都道府県 */
    private String distationPrefecture;
    /** 配送先市区町村 */
    private String distationMunicipalities;
    /** 配送先住所 */
    private String distationAddress;
    /** 配送先電話番号 */
    private String distationTel;
    /** 配送希望日時 */
    private Date deliveryTime;
    /** 支払い方法 */
    private Integer paymentMethod;
    /** 注文商品リスト */
    private List<OrderItem> orderItemList;


//    /**
//     * 注文の合計値
//     *
//     * @return orderItemListの価格の合計値
//     */
//    public Integer getTotalPrice(){
//        int sum = 0;
//        for(OrderItem orderItem: orderItemList){
//            sum += orderItem.getQuantity() * orderItem.getItem().getPrice();
//        }
//        return sum;
//    }

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
    public String getDistationName() { return distationName; }
    public void setDistationName(String distationName) { this.distationName = distationName; }
    public String getDistationEmail() { return distationEmail; }
    public void setDistationEmail(String distationEmail) { this.distationEmail = distationEmail; }
    public String getDistationZipcode() { return distationZipcode; }
    public void setDistationZipcode(String distationZipcode) { this.distationZipcode = distationZipcode; }
    public String getDistationPrefecture() { return distationPrefecture; }
    public void setDistationPrefecture(String distationPrefecture) { this.distationPrefecture = distationPrefecture; }
    public String getDistationMunicipalities() { return distationMunicipalities; }
    public void setDistationMunicipalities(String distationMunicipalities) { this.distationMunicipalities = distationMunicipalities; }
    public String getDistationAddress() { return distationAddress; }
    public void setDistationAddress(String distationAddress) { this.distationAddress = distationAddress; }
    public String getDistationTel() { return distationTel; }
    public void setDistationTel(String distationTel) { this.distationTel = distationTel; }
    public java.util.Date getDeliveryTime() { return deliveryTime; }
    public void setDeliveryTime(java.util.Date deliveryTime) { this.deliveryTime = deliveryTime; }
    public Integer getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(Integer paymentMethod) { this.paymentMethod = paymentMethod; }
    public java.util.List<OrderItem> getOrderItemList() { return orderItemList; }
    public void setOrderItemList(java.util.List<OrderItem> orderItemList) { this.orderItemList = orderItemList; }


    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", status=" + status +
                ", totalPrice=" + totalPrice +
                ", orderDate=" + orderDate +
                ", distationName='" + distationName + '\'' +
                ", distationEmail='" + distationEmail + '\'' +
                ", distationZipcode='" + distationZipcode + '\'' +
                ", distationPrefecture='" + distationPrefecture + '\'' +
                ", distationMunicipalities='" + distationMunicipalities + '\'' +
                ", distationAddress='" + distationAddress + '\'' +
                ", distationTel='" + distationTel + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", paymentMethod=" + paymentMethod +
                ", orderItemList=" + orderItemList +
                '}';
    }
} 