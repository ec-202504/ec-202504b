package com.example.domain;

/**
 * 商品情報を表すエンティティ.
 * <p>商品名や価格、カテゴリ情報などを保持します。</p>
 */
public class Item {
    /** 商品ID */
    private Integer id;
    /** 商品名 */
    private String name;
    /** 商品説明 */
    private String description;
    /** 価格 */
    private Integer price;
    /** 画像パス */
    private String imagePath;
    /** 削除フラグ */
    private Boolean deleted;
    /** サイズがある物かどうか */
    private boolean itemType;

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Integer getPrice() { return price; }
    public void setPrice(Integer price) { this.price = price; }
    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }
    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
    public boolean isItemType() {
        return itemType;
    }
    public void setItemType(boolean itemType) {
        this.itemType = itemType;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imagePath='" + imagePath + '\'' +
                ", deleted=" + deleted +
                ", itemType=" + itemType +
                '}';
    }
}