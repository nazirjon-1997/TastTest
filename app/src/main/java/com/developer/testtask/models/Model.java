package com.developer.testtask.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {
    @SerializedName("asdf")
    @Expose
    private String asdf;
    @SerializedName("qqqq")
    @Expose
    private String qqqq;

    @SerializedName("url")
    @Expose
    private String url;

    public String getAsdf() {
        return asdf;
    }

    public void setAsdf(String asdf) {
        this.asdf = asdf;
    }

    public String getQqqq() {
        return qqqq;
    }

    public void setQqqq(String qqqq) {
        this.qqqq = qqqq;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
