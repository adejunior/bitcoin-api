package com.quotebitcoin.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.quotebitcoin.exception.QuoteApiException;
import com.quotebitcoin.model.BitCoin;
import com.quotebitcoin.service.BitCoinServiceImpl;
import com.quotebitcoin.service.ConstantHelpers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/quote/")
@CrossOrigin(origins = "*")
public class BitCoinController {
    
    @Autowired
    BitCoinServiceImpl bitCoinServiceImpl;
    
    @ApiOperation(value="List all bitcoin trades.", notes="Consulta síncrona.")
    @GetMapping("all/")
    public List<BitCoin> getAllQuote() throws QuoteApiException {
	return bitCoinServiceImpl.getTradesQuoteBitCoin(ConstantHelpers.URL_TRADES.getValue());
    }
    
    @ApiOperation(value="List Top Five bitcoin trades by type trade.", notes="Consulta síncrona.")
    @ApiParam(value="Type Operation")
    @GetMapping("topfive/{typeOperation}")
    public List<BitCoin> getTopFiveHigherQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getTopFiveHigher(typeOperation);
    }
    
    @ApiOperation(value="Return value average of the trades bitcoin.", notes="Consulta síncrona.")
    @ApiParam(value="Type Operation")
    @GetMapping("average/{typeOperation}")
    public BigDecimal getAverageQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getAverage(typeOperation);
    }
    
    @ApiOperation(value="Return value median of the trades bitcoin.", notes="Consulta síncrona.")
    @ApiParam(value="Type Operation")
    @GetMapping("median/{typeOperation}")
    public BigDecimal getMedianQuote(@PathVariable String typeOperation) throws QuoteApiException {	
	return bitCoinServiceImpl.getMedian(typeOperation);
    }
    
    @ApiOperation(value="Return value standard deviation of the trades bitcoin.", notes="Consulta síncrona.")
    @ApiParam(value="Type Operation")
    @GetMapping("deviation/{typeOperation}")
    public BigDecimal getDeviationQuote(@PathVariable String typeOperation) throws QuoteApiException {
	return bitCoinServiceImpl.getStandardDeviation(typeOperation);
    }
    
}
