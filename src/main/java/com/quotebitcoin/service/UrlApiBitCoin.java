package com.quotebitcoin.service;

public enum UrlApiBitCoin {
    TRADES("https://www.mercadobitcoin.net/api/BTC/trades/1501871369/1501891200/");
    
    private String value;

    private UrlApiBitCoin(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
