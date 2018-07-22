package com.quotebitcoin.test.controller;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quotebitcoin.controller.BitCoinController;
import com.quotebitcoin.exception.QuoteApiException;
import com.quotebitcoin.model.BitCoin;
import com.quotebitcoin.service.BitCoinServiceImpl;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;


@RunWith(SpringRunner.class)
@WebMvcTest(BitCoinController.class)
public class BitCoinControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private BitCoinServiceImpl bitCoinServiceImpl;
    
    @Autowired
    ObjectMapper objectMapper;
    
    @Test
    public void getTopFiveHigherQuoteSales_shouldReturnStatusOkAndContentJson() throws Exception {
	when(bitCoinServiceImpl.getTopFiveHigher("sell")).thenReturn(
		objectMapper.readValue(
				"[{\"date\":1501871402,\"price\":9780.00000000,\"amount\":0.00919000,\"tid\":739724,\"type\":\"sell\"}," + 
				"{\"date\":1501871388,\"price\":9724.00000000,\"amount\":0.00441000,\"tid\":739722,\"type\":\"sell\"}," + 
				"{\"date\":1501871382,\"price\":9723.00000000,\"amount\":0.00200000,\"tid\":739718,\"type\":\"sell\"}," + 
				"{\"date\":1501871438,\"price\":9715.00000000,\"amount\":0.00100000,\"tid\":739730,\"type\":\"sell\"}," +
				"{\"date\":1501871459,\"price\":9714.00000000,\"amount\":0.24818000,\"tid\":739731,\"type\":\"sell\"}]",
				new TypeReference<List<BitCoin>>(){})
		);
	
        this.mockMvc.perform(get("/quote/topfive/{typeOperation}", "sell"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$", Matchers.hasSize(5)))
        	.andExpect(jsonPath("$[0].tid").value("739724"))
        	.andExpect(jsonPath("$[1].tid").value("739722"))
        	.andExpect(jsonPath("$[2].tid").value("739718"))
        	.andExpect(jsonPath("$[3].tid").value("739730"))
        	.andExpect(jsonPath("$[4].tid").value("739731"));
    }
    
    @Test
    public void getTopFiveHigherQuoteBuy_shouldReturnStatusOkAndContentJson() throws Exception {
	when(bitCoinServiceImpl.getTopFiveHigher("buy")).thenReturn(
		objectMapper.readValue(
				"[{\"date\":1501871407,\"price\":9760.00000000,\"amount\":0.01986137,\"tid\":739728,\"type\":\"buy\"}," +
				"{\"date\":1501871387,\"price\":9739.00000000,\"amount\":0.03298915,\"tid\":739721,\"type\":\"buy\"}," +
				"{\"date\":1501871387,\"price\":9738.00000000,\"amount\":0.06385831,\"tid\":739720,\"type\":\"buy\"}," +
				"{\"date\":1501871387,\"price\":9735.00000000,\"amount\":0.01776060,\"tid\":739719,\"type\":\"buy\"}," +
				"{\"date\":1501871401,\"price\":9730.00000000,\"amount\":0.09427603,\"tid\":739723,\"type\":\"buy\"}]",
				new TypeReference<List<BitCoin>>(){})
		);
	
        this.mockMvc.perform(get("/quote/topfive/{typeOperation}", "buy"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$", Matchers.hasSize(5)))
        	.andExpect(jsonPath("$[0].tid").value("739728"))
        	.andExpect(jsonPath("$[1].tid").value("739721"))
        	.andExpect(jsonPath("$[2].tid").value("739720"))
        	.andExpect(jsonPath("$[3].tid").value("739719"))
        	.andExpect(jsonPath("$[4].tid").value("739723"));
    }
    
    @Test
    public void getAverageSales_shouldReturnStatusOkAndAverageValue() throws Exception {
	when(bitCoinServiceImpl.getAverage("sell")).thenReturn(new BigDecimal(9780));
	
        this.mockMvc.perform(get("/quote/average/{typeOperation}", "sell"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("9780"));
    }
    
    @Test
    public void getAverageBuy_shouldReturnStatusOkAndAverageValue() throws Exception {
	when(bitCoinServiceImpl.getAverage("buy")).thenReturn(new BigDecimal(9760));
	
        this.mockMvc.perform(get("/quote/average/{typeOperation}", "buy"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("9760"));
    }
    
    @Test
    public void getMedianSales_shouldReturnStatusOkAndMedianValue() throws Exception {
	when(bitCoinServiceImpl.getMedian("sell")).thenReturn(new BigDecimal(9730));
	
        this.mockMvc.perform(get("/quote/median/{typeOperation}", "sell"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("9730"));
    }
    
    @Test
    public void getMedianBuy_shouldReturnStatusOkAndMedianValue() throws Exception {
	when(bitCoinServiceImpl.getMedian("buy")).thenReturn(new BigDecimal(9710.5));
	
        this.mockMvc.perform(get("/quote/median/{typeOperation}", "buy"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("9710.5"));
    }
    
    @Test
    public void getDeviationSales_shouldReturnStatusOkAndDeviationValue() throws Exception {
	when(bitCoinServiceImpl.getStandardDeviation("sell")).thenReturn(new BigDecimal("20.91"));
	
        this.mockMvc.perform(get("/quote/deviation/{typeOperation}", "sell"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("20.91"));
    }
    
    @Test
    public void getDeviationBuy_shouldReturnStatusOkAndDeviationValue() throws Exception {
	when(bitCoinServiceImpl.getStandardDeviation("buy")).thenReturn(new BigDecimal("17.76"));
	
        this.mockMvc.perform(get("/quote/deviation/{typeOperation}", "buy"))
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$").value("17.76"));
    }

}
