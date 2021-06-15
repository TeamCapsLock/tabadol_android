package com.example.tabadol.api;

public class AddPostForm {

    String body;
    String category;
    String type;
    Integer weight;

    public AddPostForm(String body, String category, String type, Integer weight) {
        this.body = body;
        this.category = category;
        this.type = type;
        this.weight = weight;
    }

    public AddPostForm() {
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        return "AddPostForm{" +
                "body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", type='" + type + '\'' +
                ", weight=" + weight +
                '}';
    }
}
