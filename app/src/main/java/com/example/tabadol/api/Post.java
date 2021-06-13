package com.example.tabadol.api;

public class Post {

    long id;
    String body;
    String category;
    String type;
    int weight;
    boolean available;
    String createdAt;
    String offerType;
    User user;

    public Post(long id, String body, String category, String type, int weight, boolean available, String createdAt, String offerType, User user) {
        this.id = id;
        this.body = body;
        this.category = category;
        this.type = type;
        this.weight = weight;
        this.available = available;
        this.createdAt = createdAt;
        this.offerType = offerType;
        this.user = user;
    }

    public Post() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                ", available=" + available +
                ", createdAt='" + createdAt + '\'' +
                ", offerType='" + offerType + '\'' +
                ", user=" + user +
                '}';
    }
}
