package com.example.tabadol.api;

public class RateUser {
    int rateValue;

    public RateUser() {
    }

    public RateUser(int rateValue) {
        this.rateValue = rateValue;
    }

    public int getRateValue() {
        return rateValue;
    }

    public void setRateValue(int rateValue) {
        this.rateValue = rateValue;
    }

    @Override
    public String toString() {
        return "RateUser{" +
                "rateValue=" + rateValue +
                '}';
    }
}
