package com.example.tabadol.api;

public class AcceptOffer {
    long source_id;
    long destination_id;

    public AcceptOffer(long source_id, long destination_id) {
        this.source_id = source_id;
        this.destination_id = destination_id;
    }

    public AcceptOffer() {
    }

    public long getSource_id() {
        return source_id;
    }

    public void setSource_id(long source_id) {
        this.source_id = source_id;
    }

    public long getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(long destination_id) {
        this.destination_id = destination_id;
    }

    @Override
    public String toString() {
        return "AcceptOffer{" +
                "source_id=" + source_id +
                ", destination_id=" + destination_id +
                '}';
    }
}
