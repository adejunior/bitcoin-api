package com.quotebitcoin.exception;

public class QuoteApiException extends RuntimeException{

    private static final long serialVersionUID = 1L;

	public QuoteApiException(String msg) {
		super(msg);
	}
}


