package com.example.tabadol.api;

import java.util.List;

public class FinishedOffers {

    Post postThatSentTheOffers;
    List<Post> postsThatReceivedTheSentOffer;

    Post postHasReceivedTheOffers;
    List<Post> theReceivedOffersForOnePost;

    public FinishedOffers() {
    }

    public FinishedOffers(Post postThatSentTheOffers, List<Post> postsThatReceivedTheSentOffer, Post postHasReceivedTheOffers, List<Post> theReceivedOffersForOnePost) {
        this.postThatSentTheOffers = postThatSentTheOffers;
        this.postsThatReceivedTheSentOffer = postsThatReceivedTheSentOffer;
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
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

    public Post getPostHasReceivedTheOffers() {
        return postHasReceivedTheOffers;
    }

    public void setPostHasReceivedTheOffers(Post postHasReceivedTheOffers) {
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
    }

    public List<Post> getTheReceivedOffersForOnePost() {
        return theReceivedOffersForOnePost;
    }

    public void setTheReceivedOffersForOnePost(List<Post> theReceivedOffersForOnePost) {
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
    }

    @Override
    public String toString() {
        return "FinishedOffers{" +
                "postThatSentTheOffers=" + postThatSentTheOffers +
                ", postsThatReceivedTheSentOffer=" + postsThatReceivedTheSentOffer +
                ", postHasReceivedTheOffers=" + postHasReceivedTheOffers +
                ", theReceivedOffersForOnePost=" + theReceivedOffersForOnePost +
                '}';
    }
}

