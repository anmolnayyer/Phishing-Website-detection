package com.example.demo.model;

public class WebsiteFeatures {
    private String url;
    private boolean sslValid;
    private boolean hasAtSymbol;
    private int urlLength;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
        this.urlLength = url.length();
        this.hasAtSymbol = url.contains("@");
    }

    public boolean isSslValid() {
        return sslValid;
    }

    public void setSslValid(boolean sslValid) {
        this.sslValid = sslValid;
    }

    public boolean hasAtSymbol() {
        return hasAtSymbol;
    }

    public int getUrlLength() {
        return urlLength;
    }
}