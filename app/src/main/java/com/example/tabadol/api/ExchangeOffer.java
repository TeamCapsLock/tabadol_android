package com.example.tabadol.api;

public class ExchangeOffer {

    private Long myPostId;

    public ExchangeOffer(Long myPostId) {
        this.myPostId = myPostId;
    }

    public ExchangeOffer() {
    }

    public Long getMyPostId() {
        return myPostId;
    }

    public void setMyPostId(Long myPostId) {
        this.myPostId = myPostId;
    }

    @Override
    public String toString() {
        return "ExchangeOffer{" +
                "myPostId=" + myPostId +
                '}';
    }
}
