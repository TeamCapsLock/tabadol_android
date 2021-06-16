package com.example.tabadol.api;

public class Offer {
    private Post sourcePost;
    private Post destinationPost;

    public Offer(Post s, Post d){
        this.sourcePost = s;
        this.destinationPost = d;
    }

    public Offer(){

    }

    public Post getSourcePost() {
        return sourcePost;
    }

    public void setSourcePost(Post sourcePost) {
        this.sourcePost = sourcePost;
    }

    public Post getDestinationPost() {
        return destinationPost;
    }

    public void setDestinationPost(Post destinationPost) {
        this.destinationPost = destinationPost;
    }
}
