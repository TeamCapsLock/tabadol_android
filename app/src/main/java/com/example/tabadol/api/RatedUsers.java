package com.example.tabadol.api;

import java.util.List;

public class RatedUsers {
    List<Long> ratedUsers;

    public RatedUsers(List<Long> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }

    public RatedUsers() {
    }

    public List<Long> getRatedUsers() {
        return ratedUsers;
    }

    public void setRatedUsers(List<Long> ratedUsers) {
        this.ratedUsers = ratedUsers;
    }

    @Override
    public String toString() {
        return "RatedUsers{" +
                "ratedUsers=" + ratedUsers +
                '}';
    }
}
