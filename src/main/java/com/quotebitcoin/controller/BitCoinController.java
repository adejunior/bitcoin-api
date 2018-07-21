package com.quotebitcoin.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotebitcoin.exception.QuoteApiException;
import com.quotebitcoin.model.BitCoin;
import com.quotebitcoin.service.BitCoinServiceImpl;

@RestController
@RequestMapping("/quote/")
public class BitCoinController {
    
    @Autowired
    BitCoinServiceImpl bitCoinServiceImpl;
    
    @GetMapping("topfive/{typeOperation}")
    public List<BitCoin> getTopFiveHigherQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getTopFiveHigher(typeOperation);
    }
    
    @GetMapping("average/{typeOperation}")
    public BigDecimal getAverageQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getAverage(typeOperation);
    }
    
    @GetMapping("median/{typeOperation}")
    public BigDecimal getMedianQuote(@PathVariable String typeOperation) throws QuoteApiException {	
	return bitCoinServiceImpl.getMedian(typeOperation);
    }
    
    @GetMapping("deviation/{typeOperation}")
    public BigDecimal getDeviationQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getStandardDeviation(typeOperation);
    }
    
}
