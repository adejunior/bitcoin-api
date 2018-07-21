package com.quotebitcoin.service;

public enum ConstantHelpers {
    URL_TRADES("https://www.mercadobitcoin.net/api/BTC/trades/1501871369/1501891200/"),
    TYPE_OPERATION_SELL("sell"),
    TYPE_OPERATION_BUY("buy");
    
    private String value;

    private ConstantHelpers(String s) {
        value = s;
    }

    public String getValue() {
        return value;
    }
}
