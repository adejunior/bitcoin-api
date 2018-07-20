package com.quotebitcoin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.quotebitcoin.model.BitCoin;

@Service
public class BitCoinServiceImpl {
    
    @Autowired
    RestTemplate restTemplate;
        
    public List<BitCoin> getQuoteBitCoin(String url) {
	ResponseEntity<List<BitCoin>> response = restTemplate.exchange(
		  url,
		  HttpMethod.GET,
		  null,
		  new ParameterizedTypeReference<List<BitCoin>>(){});	
	return response.getBody();
    }

}
