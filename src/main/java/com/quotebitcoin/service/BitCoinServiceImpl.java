package com.quotebitcoin.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quotebitcoin.exception.QuoteApiException;
import com.quotebitcoin.model.BitCoin;


@Service
public class BitCoinServiceImpl {

    @Autowired
    RestTemplate restTemplate;

    private ResponseEntity<List<BitCoin>> getQuoteBitCoinRequest(String url) {
	return restTemplate.exchange(
		url,
		HttpMethod.GET,
		null,
		new ParameterizedTypeReference<List<BitCoin>>(){});	
    }

    public List<BitCoin> getTradesQuoteBitCoin(String urlSourceApiBitCoin) throws QuoteApiException {	
	return getQuoteBitCoinRequest(urlSourceApiBitCoin).getBody().stream().collect(Collectors.toList());
    }


    public List<BitCoin> getListbitCoinByTypeOperation(String typeOperation) throws QuoteApiException {
	if(!typeOperation.equals("sell") && !typeOperation.equals("buy"))
	    throw new QuoteApiException("Invalid Type operation for Quote BitCoin");
	return getTradesQuoteBitCoin(ConstantHelpers.URL_TRADES.getValue())
		.stream()
		.filter((x) -> x.getType().equals(typeOperation))
		.collect(Collectors.toList());
	
    }

    public List<BitCoin> getTopFiveHigher(String typeOperation) throws QuoteApiException {
	List<BitCoin> listBitCoinByTypeOperation = getListbitCoinByTypeOperation(typeOperation);
	return listBitCoinByTypeOperation
		.stream()
		.sorted(Comparator.comparing(BitCoin::getPrice).reversed())
		.limit(5)
		.collect(Collectors.toList());
    }

    public BigDecimal getAverage(String typeOperation) throws QuoteApiException {
	List<BitCoin> listBitCoinByTypeOperation = getListbitCoinByTypeOperation(typeOperation);
	return listBitCoinByTypeOperation
		.stream()		
		.map(x -> x.getPrice())
		.reduce((x, y) -> x.add(y)).get()
		.divide(BigDecimal.valueOf(listBitCoinByTypeOperation.size()), 2, RoundingMode.HALF_EVEN);
    }    

    public BigDecimal getMedian(String typeOperation) throws QuoteApiException {
	List<BitCoin> listBitCoinByTypeOperation = getListbitCoinByTypeOperation(typeOperation);
	Double[] arrayPrices = listBitCoinByTypeOperation
                    		.stream()
                    		.map(x->x.getPrice().doubleValue())
                    		.toArray(Double[]::new);
	return new BigDecimal(
		new Median().evaluate(ArrayUtils.toPrimitive(arrayPrices)));

    }

    public BigDecimal getStandardDeviation(String typeOperation) throws QuoteApiException {
	List<BitCoin> listBitCoinByTypeOperation = getListbitCoinByTypeOperation(typeOperation);
	Double[] arrayPrices = listBitCoinByTypeOperation
				.stream()
				.map(x->x.getPrice().doubleValue()).toArray(Double[]::new);
	return BigDecimal.valueOf(
		new DescriptiveStatistics(ArrayUtils.toPrimitive(arrayPrices))
		.getStandardDeviation()).setScale(2, RoundingMode.HALF_EVEN);		
    }

}
