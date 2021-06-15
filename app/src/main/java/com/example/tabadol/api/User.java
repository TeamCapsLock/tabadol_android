package com.example.tabadol.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class User implements Parcelable {
    Long id;
    String username;
    String firstname;
    String lastname;
    String image;
    String email;
    String skills;
    String bio;
    Integer numberOfFollowers;
    Integer numberOfFollowing;
    Double rating;
    String phone;
    List<Post> posts;








    public User() {
    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            id = null;
        } else {
            id = in.readLong();
        }
        username = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        image = in.readString();
        email = in.readString();
        skills = in.readString();
        bio = in.readString();
        if (in.readByte() == 0) {
            numberOfFollowers = null;
        } else {
            numberOfFollowers = in.readInt();
        }
        if (in.readByte() == 0) {
            numberOfFollowing = null;
        } else {
            numberOfFollowing = in.readInt();
        }
        if (in.readByte() == 0) {
            rating = null;
        } else {
            rating = in.readDouble();
        }
        phone = in.readString();
        posts = in.createTypedArrayList(Post.CREATOR);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getNumberOfFollowers() {
        return numberOfFollowers;
    }

    public void setNumberOfFollowers(Integer numberOfFollowers) {
        this.numberOfFollowers = numberOfFollowers;
    }

    public Integer getNumberOfFollowing() {
        return numberOfFollowing;
    }

    public void setNumberOfFollowing(Integer numberOfFollowing) {
        this.numberOfFollowing = numberOfFollowing;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public User(Long id, String username, String firstname, String lastname, String image, String email, String skills, String bio, Integer numberOfFollowers, Integer numberOfFollowing, Double rating, String phone, List<Post> posts) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
        this.email = email;
        this.skills = skills;
        this.bio = bio;
        this.numberOfFollowers = numberOfFollowers;
        this.numberOfFollowing = numberOfFollowing;
        this.rating = rating;
        this.phone = phone;
        this.posts = posts;
    }

    public User(long id, String username, String firstname, String lastname, String image) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.image = image;
    }


    public User(long id, String username) {
        this.id = id;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + "\n" +
                ", image='" + image + '\'' +
                "\n, email='" + email + '\'' +
                ", skills='" + skills + '\'' +
                "\n, bio='" + bio + '\'' +
                "\n, numberOfFollowers=" + numberOfFollowers +
                ", numberOfFollowing=" + numberOfFollowing +
                ", rating=" + rating +
                "\n, phone='" + phone + '\'' +
                "\n, posts=" + posts +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (id == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeLong(id);
        }
        dest.writeString(username);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(image);
        dest.writeString(email);
        dest.writeString(skills);
        dest.writeString(bio);
        if (numberOfFollowers == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfFollowers);
        }
        if (numberOfFollowing == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(numberOfFollowing);
        }
        if (rating == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(rating);
        }
        dest.writeString(phone);
        dest.writeTypedList(posts);
    }
}
