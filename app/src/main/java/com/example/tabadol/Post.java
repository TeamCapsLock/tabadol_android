package com.example.tabadol;

public class Post {
    // TODO: create the post class
    private long id;
    private String body;
    private String category;
    private String type;
    private Integer weight;
    private Boolean isAvailable;
    private String createdAt;
    private String offerType;

    public Post(){

    }

    public Post(String body, String category, String type, Integer weight, Boolean available, String createdAt) {
        this.createdAt = createdAt;
        this.body = body;
        this.category = category;
        this.type = type;
        this.weight = weight;
        this.isAvailable = true;
    }

    public long getId() {
        return id;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                ", isAvailable=" + isAvailable +
                ", createdAt='" + createdAt + '\'' +
                ", offerType='" + offerType + '\'' +
                '}';
    }
}
