package com.quotebitcoin.test.service;

import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotebitcoin.model.BitCoin;
import com.quotebitcoin.service.BitCoinServiceImpl;
import com.quotebitcoin.service.UrlApiBitCoin;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitCoinServiceTest {
    
    private String jsonQuoteBitCoin = 
	  "[{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718, \"type\":\"sell\"},"
    	+ "{\"date\":1501871387,\"price\":9735.00000000,\"amount\":0.01776060,\"tid\":739719, \"type\":\"buy\"},"
    	+ "{\"date\":1501871387,\"price\":9738.00000000,\"amount\":0.06385831,\"tid\":739720, \"type\":\"buy\"},"
    	+ "{\"date\":1501871387,\"price\":9738.00000000,\"amount\":0.03298915,\"tid\":739721, \"type\":\"buy\"},"
    	+ "{\"date\":1501871388,\"price\":9723.00000000,\"amount\":0.00441000,\"tid\":739722, \"type\":\"sell\"},"
    	+ "{\"date\":1501871401,\"price\":9738.00000000,\"amount\":0.09427603,\"tid\":739723, \"type\":\"buy\"}]";
    
    @Autowired
    RestTemplate restTemplate;
    
    private MockRestServiceServer mockServer;
    
    @Autowired
    private BitCoinServiceImpl bitCoinServiceImpl;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Before
    public void setUp() {
        RestGatewaySupport gateway = new RestGatewaySupport();
        gateway.setRestTemplate(restTemplate);
        mockServer = MockRestServiceServer.createServer(gateway);
        mockServer.expect(
		once(), requestTo(UrlApiBitCoin.TRADES.getValue()))
	            .andRespond(withSuccess(jsonQuoteBitCoin, MediaType.APPLICATION_JSON));
    }
    
    @Test(expected=AssertionError.class)
    public void getQuoteBitCoinIncorrectUrl_shouldReturnAssertionError() {
	bitCoinServiceImpl.getQuoteBitCoin("");
    }
            
    @Test
    public void getListQuoteBitCoins_shouldReturnList() {			
	List<BitCoin> listBitCoin = bitCoinServiceImpl.getQuoteBitCoin(UrlApiBitCoin.TRADES.getValue());	
	Assert.assertNotNull(listBitCoin);	
    }
        
    @Test
    public void getQuoteBitCoin_shouldReturnBitCoinObjectQuoteEqualsExpected() {
	try {
	    
	    BitCoin bitCoinExpected = objectMapper.readValue(
	    	"{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718, \"type\":\"sell\"}"
	    	, BitCoin.class);
	    
	    BitCoin bitCoinActual = bitCoinServiceImpl.getQuoteBitCoin(UrlApiBitCoin.TRADES.getValue())
		    .stream()
		    .filter(x -> x.getTid().equals(new BigDecimal(739718)))
		    .findFirst()
		    .get();
	    
	    Assert.assertEquals(bitCoinExpected, bitCoinActual);
	} catch (IOException e) {
	    e.printStackTrace();
	}		
    }
     
    
}
