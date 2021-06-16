package com.example.tabadol.api;

import java.util.List;

public class SentOffers {

    Post postThatSentTheOffers;
    List<Post> postsThatReceivedTheSentOffer;

    public SentOffers() {
    }

    public SentOffers(Post postThatSentTheOffers, List<Post> postsThatReceivedTheSentOffer) {
        this.postThatSentTheOffers = postThatSentTheOffers;
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
    }

    public Post getPostThatSentTheOffers() {
        return postThatSentTheOffers;
    }

    public void setPostThatSentTheOffers(Post postThatSentTheOffers) {
        this.postThatSentTheOffers = postThatSentTheOffers;
    }

    public List<Post> getPostsThatReceivedTheSentOffer() {
        return postsThatReceivedTheSentOffer;
    }

    public void setPostsThatReceivedTheSentOffer(List<Post> postsThatReceivedTheSentOffer) {
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
    }



    @Override
    public String toString() {
        return "SentOffers{" +
                "postThatSentTheOffers=" + postThatSentTheOffers +
                ", postsThatReceivedTheSentOffer=" + postsThatReceivedTheSentOffer +
                '}';
    }
}
