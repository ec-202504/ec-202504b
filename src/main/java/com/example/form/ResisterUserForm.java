package com.example.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * 会員登録用のフォーム.
 */
public class ResisterUserForm {
    /** 姓 */
    @NotBlank(message = "名前を入力して下さい")
    private String lastName;

    /** 名 */
    @NotBlank(message = "名前を入力して下さい")
    private String firstName;

    /** メールアドレス */
    @NotBlank(message = "メールアドレスを入力してください")
    @Pattern(regexp = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$", message = "メールアドレスの形式が不正です")
    private String email;

    /** パスワード */
    @NotBlank(message = "パスワードを入力してください")
    @Size(min = 8, max = 16, message = "パスワードは８文字以上１６文字以内で設定してください")
    private String password;

    /** 確認用パスワード */
    @NotBlank(message = "確認用パスワードを入力してください")
    private String confirmPassword;

    /** 郵便番号 */
    @NotBlank(message = "郵便番号を入力してください")
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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


    @Override
    public String toString() {
        return "ResisterUserForm{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", prefecture='" + prefecture + '\'' +
                ", municipalities='" + municipalities + '\'' +
                ", address='" + address + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
