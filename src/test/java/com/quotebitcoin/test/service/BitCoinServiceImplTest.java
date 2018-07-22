package com.quotebitcoin.test.service;

import static org.springframework.test.web.client.ExpectedCount.once;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.support.RestGatewaySupport;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotebitcoin.model.BitCoin;
import com.quotebitcoin.service.BitCoinServiceImpl;
import com.quotebitcoin.service.ConstantHelpers;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BitCoinServiceImplTest {

    private String jsonQuoteBitCoin = 
	    	      "[{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718,\"type\":\"sell\"},"
		    + "{\"date\":1501871387,\"price\":9735.00000000,\"amount\":0.01776060,\"tid\":739719,\"type\":\"buy\"},"
		    + "{\"date\":1501871387,\"price\":9738.00000000,\"amount\":0.06385831,\"tid\":739720,\"type\":\"buy\"},"
		    + "{\"date\":1501871387,\"price\":9739.00000000,\"amount\":0.03298915,\"tid\":739721,\"type\":\"buy\"},"
		    + "{\"date\":1501871388,\"price\":9724.00000000,\"amount\":0.00441000,\"tid\":739722,\"type\":\"sell\"},"
		    + "{\"date\":1501871401,\"price\":9730.00000000,\"amount\":0.09427603,\"tid\":739723,\"type\":\"buy\"},"
		    + "{\"date\":1501871402,\"price\":9780.00000000,\"amount\":0.00919000,\"tid\":739724,\"type\":\"sell\"},"
		    + "{\"date\":1501871402,\"price\":9711.00000000,\"amount\":0.01513440,\"tid\":739725,\"type\":\"sell\"},"
		    + "{\"date\":1501871402,\"price\":9710.00000000,\"amount\":0.10493560,\"tid\":739726,\"type\":\"buy\"},"
		    + "{\"date\":1501871406,\"price\":9709.00000000,\"amount\":0.21070000,\"tid\":739727,\"type\":\"sell\"},"
		    + "{\"date\":1501871407,\"price\":9760.00000000,\"amount\":0.01986137,\"tid\":739728,\"type\":\"buy\"},"
		    + "{\"date\":1501871435,\"price\":9710.00000000,\"amount\":0.00300000,\"tid\":739729,\"type\":\"sell\"},"
		    + "{\"date\":1501871438,\"price\":9715.00000000,\"amount\":0.00100000,\"tid\":739730,\"type\":\"sell\"},"
		    + "{\"date\":1501871459,\"price\":9714.00000000,\"amount\":0.24818000,\"tid\":739731,\"type\":\"sell\"},"
		    + "{\"date\":1501871481,\"price\":9730.00000000,\"amount\":0.01070000,\"tid\":739732,\"type\":\"buy\"},"
		    + "{\"date\":1501871517,\"price\":9710.00000000,\"amount\":0.13109621,\"tid\":739733,\"type\":\"sell\"},"
		    + "{\"date\":1501871517,\"price\":9713.00000000,\"amount\":0.00513455,\"tid\":739734,\"type\":\"buy\"},"
		    + "{\"date\":1501871517,\"price\":9701.25000000,\"amount\":0.00209010,\"tid\":739735,\"type\":\"sell\"},"
		    + "{\"date\":1501871518,\"price\":9707.00000000,\"amount\":0.02580520,\"tid\":739736,\"type\":\"sell\"},"
		    + "{\"date\":1501871518,\"price\":9702.00000000,\"amount\":0.07700000,\"tid\":739737,\"type\":\"buy\"},"
		    + "{\"date\":1501871518,\"price\":9703.00000000,\"amount\":0.05000000,\"tid\":739738,\"type\":\"sell\"}]";

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
		once(), requestTo(ConstantHelpers.URL_TRADES.getValue()))
	.andRespond(withSuccess(jsonQuoteBitCoin, MediaType.APPLICATION_JSON));
    }

    @Test(expected=AssertionError.class)
    public void getQuoteBitCoinIncorrectUrl_shouldReturnAssertionError() {
	bitCoinServiceImpl.getTradesQuoteBitCoin("");
    }

    @Test
    public void getListQuoteBitCoins_shouldReturnList() {			
	List<BitCoin> listBitCoin = bitCoinServiceImpl.getTradesQuoteBitCoin(ConstantHelpers.URL_TRADES.getValue());	
	Assert.assertNotNull(listBitCoin);	
    }

    @Test
    public void getQuoteBitCoin_shouldReturnBitCoinObjectQuoteEqualsExpected() {
	try {

	    BitCoin bitCoinExpected = objectMapper.readValue(
		    "{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718, \"type\":\"sell\"}"
		    , BitCoin.class);

	    BitCoin bitCoinActual = bitCoinServiceImpl.getTradesQuoteBitCoin(ConstantHelpers.URL_TRADES.getValue())
		    .stream()
		    .filter(x -> x.getTid().equals(new BigDecimal(739718)))
		    .findFirst()
		    .get();

	    Assert.assertEquals(bitCoinExpected, bitCoinActual);
	} catch (IOException e) {
	    e.printStackTrace();
	}		
    }

    @Test
    public void getListQuoteBitCoin_shouldReturnTopFiveHigherSales() throws JsonParseException, JsonMappingException, IOException {
	List<BitCoin> listHigherSalesExpected = objectMapper.readValue(
		"[{\"date\":1501871402,\"price\":9780.00000000,\"amount\":0.00919000,\"tid\":739724,\"type\":\"sell\"}," + 
			"{\"date\":1501871388,\"price\":9724.00000000,\"amount\":0.00441000,\"tid\":739722,\"type\":\"sell\"}," + 
			"{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718,\"type\":\"sell\"}," + 
			"{\"date\":1501871438,\"price\":9715.00000000,\"amount\":0.00100000,\"tid\":739730,\"type\":\"sell\"}," +
			"{\"date\":1501871459,\"price\":9714.00000000,\"amount\":0.24818000,\"tid\":739731,\"type\":\"sell\"}]",
			new TypeReference<List<BitCoin>>(){} );

	Assert.assertEquals(
		listHigherSalesExpected, 
		bitCoinServiceImpl.getTopFiveHigher(ConstantHelpers.TYPE_OPERATION_SELL.getValue())
		);
    }

    @Test
    public void getListQuoteBitCoin_shouldReturnTopFiveHigherBuys() throws JsonParseException, JsonMappingException, IOException {
	List<BitCoin> listHigherSalesExpected = objectMapper.readValue(
		"[{\"date\":1501871407,\"price\":9760.00000000,\"amount\":0.01986137,\"tid\":739728,\"type\":\"buy\"}," +
			"{\"date\":1501871387,\"price\":9739.00000000,\"amount\":0.03298915,\"tid\":739721,\"type\":\"buy\"}," +
			"{\"date\":1501871387,\"price\":9738.00000000,\"amount\":0.06385831,\"tid\":739720,\"type\":\"buy\"}," +
			"{\"date\":1501871387,\"price\":9735.00000000,\"amount\":0.01776060,\"tid\":739719,\"type\":\"buy\"}," +
			"{\"date\":1501871401,\"price\":9730.00000000,\"amount\":0.09427603,\"tid\":739723,\"type\":\"buy\"}]", 
			new TypeReference<List<BitCoin>>(){} );

	Assert.assertEquals(
		listHigherSalesExpected, 
		bitCoinServiceImpl.getTopFiveHigher(ConstantHelpers.TYPE_OPERATION_BUY.getValue())
		);
    }

    @Test
    public void geAverage_shouldReturnAvgBuys() {	
	Assert.assertEquals(
		BigDecimal.valueOf(9728.56), 
		bitCoinServiceImpl.getAverage(ConstantHelpers.TYPE_OPERATION_BUY.getValue())
		);
    }

    @Test
    public void getAverage_shouldReturnAvgSales(){	
	Assert.assertEquals(
		BigDecimal.valueOf(9717.27), 
		bitCoinServiceImpl.getAverage(ConstantHelpers.TYPE_OPERATION_SELL.getValue())
		);
    }

    @Test
    public void getMedian_shoulReturnValueMedianBuy(){
	Assert.assertEquals(
		BigDecimal.valueOf(9730), 
		bitCoinServiceImpl.getMedian(ConstantHelpers.TYPE_OPERATION_BUY.getValue())
		);
    }

    @Test
    public void getMedian_shoulReturnValueMedianSales(){
	Assert.assertEquals(
		BigDecimal.valueOf(9710.5), 
		bitCoinServiceImpl.getMedian(ConstantHelpers.TYPE_OPERATION_SELL.getValue())
		);	
    }

    @Test
    public void getStandardDeviation_shoulReturnValueStandardDeviationBuy(){
	Assert.assertEquals(
		BigDecimal.valueOf(17.76), 
		bitCoinServiceImpl.getStandardDeviation(ConstantHelpers.TYPE_OPERATION_BUY.getValue())
		);
	}

    @Test
    public void getStandardDeviation_shoulReturnValueStandardDeviationSales(){	
	Assert.assertEquals(
		BigDecimal.valueOf(20.91), 
		bitCoinServiceImpl.getStandardDeviation(ConstantHelpers.TYPE_OPERATION_SELL.getValue())
		);
    }


}
