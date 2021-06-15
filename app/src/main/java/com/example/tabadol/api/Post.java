package com.example.tabadol.api;

import android.os.Parcel;
import android.os.Parcelable;


public class Post implements Parcelable {

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

    protected Post(Parcel in) {
        id = in.readLong();
        body = in.readString();
        category = in.readString();
        type = in.readString();
        weight = in.readInt();
        available = in.readByte() != 0;
        createdAt = in.readString();
        offerType = in.readString();
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(body);
        dest.writeString(category);
        dest.writeString(type);
        dest.writeInt(weight);
        dest.writeByte((byte) (available ? 1 : 0));
        dest.writeString(createdAt);
        dest.writeString(offerType);
    }
}
