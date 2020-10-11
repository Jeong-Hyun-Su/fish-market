package com.pirates.market.Interfaces;

import com.pirates.market.Domain.BusinessTime;
import com.pirates.market.Domain.Holiday;
import com.pirates.market.Domain.Market;
import com.pirates.market.Services.MarketService;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(MarketController.class)
class MarketControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private MarketService marketService;


    @Test
    public void add() throws Exception{
        given(marketService.addMarket(any())).will(invocation -> {
            List<BusinessTime> businessTimes = new ArrayList<>();
            businessTimes.add(new BusinessTime("Thursday","13:00","23:00"));
            businessTimes.add(new BusinessTime("Friday", "09:00", "18:00"));
            businessTimes.add(new BusinessTime("Saturday", "09:00", "23:00"));
            businessTimes.add(new BusinessTime("Sunday", "09:00", "23:00"));

            return Market.builder().id(1)
                    .name("인어수산").owner("장인어").description("인천소래포구 종합어시장 갑각류센터 인어수산")
                    .level(2).address("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1층 1호")
                    .phone("010-1111-2222").businessTimes(businessTimes).build();
        });


        mvc.perform(post("/add").contentType(MediaType.APPLICATION_JSON)
                                          .content("{\"id\":1, \"name\":\"인어수산\", \"owner\":\"장인어\", \"description\":" +
                                                    "\"인천소래포구 종합어시장 갑각류센터 인어수산\"}"))
                                          .andExpect(status().isCreated())
                                          .andExpect(header().string("location", "/market/1"))
                                          .andExpect(content().string("{}"));

        verify(marketService, times(1)).addMarket(any());
    }

    @Test
    public void holiday() throws Exception{
        mvc.perform(patch("/holiday").contentType(MediaType.APPLICATION_JSON)
                                                .content("{\"id\":1,\"holidays\": [ \"2020-10-10\", \"2020-10-11\"]}"))
                                                .andExpect(status().isOk());

        List<Holiday> holidays = new ArrayList<>();
        holidays.add(new Holiday("2020-10-10"));
        holidays.add(new Holiday("2020-10-11"));

        verify(marketService, times(1)).setHolidays(1, holidays);
    }
}