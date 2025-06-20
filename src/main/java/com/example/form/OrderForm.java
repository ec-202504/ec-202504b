package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;

public class OrderForm {
    /** 注文ID */
    private Integer orderId;

    /** 名前 */
    private String name;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "メールアドレスの形式が不正です")
    private String email;

    /** 郵便番号 */
    @Pattern(regexp = "^\\d{3}-\\d{4}$", message = "郵便番号はXXX-XXXXの形式で入力してください")
    private String zipcode;

    /** 都道府県 */
    @NotBlank(message = "住所を入力して下さい")
    private String prefecture;

    /** 市区町村 */
    @NotBlank(message = "住所を入力して下さい")
    private String municipalities;

    /** 住所 */
    @NotBlank(message = "住所を入力して下さい")
    private String address;

    /** 電話番号 */
    @NotBlank(message = "電話番号を入力してください")
    @Pattern(regexp = "^\\d{2,4}-\\d{2,4}-\\d{3,4}$", message = "電話番号はXXXX-XXXX-XXXXの形式で入力してください")
    private String telephone;

    /** 配達日時 */
    @NotBlank(message = "配達日時を入力して下さい")
    //TODO:現時点から3時間後以前が入力された場合→「今から3時間後の日時をご入力ください」
    private String deliveryTime; //配達日
    private Integer paymentMethod; // 支払方法


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getPrefecture() {
        return prefecture;
    }

    public void setPrefecture(String prefecture) {
        this.prefecture = prefecture;
    }

    public String getMunicipalities() {
        return municipalities;
    }

    public void setMunicipalities(String municipalities) {
        this.municipalities = municipalities;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public Integer getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Integer paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @Override
    public String toString() {
        return "OrderForm{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", prefecture='" + prefecture + '\'' +
                ", municipalities='" + municipalities + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", paymentMethod=" + paymentMethod +
                '}';
    }
}