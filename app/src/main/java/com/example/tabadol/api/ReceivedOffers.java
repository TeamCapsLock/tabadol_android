package com.example.tabadol.api;

import java.util.List;

public class ReceivedOffers {

    Post postHasReceivedTheOffers;
    List<Post> theReceivedOffersForOnePost;

    public ReceivedOffers(Post postHasReceivedTheOffers, List<Post> theReceivedOffersForOnePost) {
        this.postHasReceivedTheOffers = postHasReceivedTheOffers;
        this.theReceivedOffersForOnePost = theReceivedOffersForOnePost;
    }

    public ReceivedOffers() {
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
        return "ReceivedOffers{" +
                "postHasReceivedTheOffers=" + postHasReceivedTheOffers +
                ", theReceivedOffersForOnePost=" + theReceivedOffersForOnePost +
                '}';
    }
}
