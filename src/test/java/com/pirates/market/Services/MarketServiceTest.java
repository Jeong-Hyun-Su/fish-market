package com.pirates.market.Services;

import com.pirates.market.Domain.BusinessTime;
import com.pirates.market.Domain.Market;
import com.pirates.market.Domain.MarketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.yaml.snakeyaml.error.Mark;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MarketServiceTest {
    private MarketService marketService;

    @Mock
    private MarketRepository marketRepository;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Thursday","13:00","23:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "18:00"));
        businessTimes.add(new BusinessTime("Saturday", "09:00", "23:00"));
        businessTimes.add(new BusinessTime("Sunday", "09:00", "23:00"));
        Market market = Market.builder().id(1)
                                        .name("인어수산").owner("장인어").description("인천소래포구 종합어시장 갑각류센터 인어수산")
                                        .level(2).address("인천광역시 남동구 논현동 680-1 소래포구 종합어시장 1층 1호")
                                        .phone("010-1111-2222").businessTimes(businessTimes).build();


        marketService = new MarketService(marketRepository);
    }

    @Test
    public void addMarket(){
        List<BusinessTime> businessTimes = new ArrayList<>();
        businessTimes.add(new BusinessTime("Thursday","09:00","24:00"));
        businessTimes.add(new BusinessTime("Friday", "09:00", "24:00"));
        businessTimes.add(new BusinessTime("Saturday", "09:00", "24:00"));
        businessTimes.add(new BusinessTime("Sunday", "09:00", "24:00"));
        Market market = Market.builder().id(1)
                .name("해적수산").owner("박해적").description("노량진 시장 광어, 참돔 등 싱싱한 고퀄 활어 전문 횟집")
                .level(1).address("서울 동작구 노량진동 13-8 노량진수산시장 활어 001")
                .phone("010-1234-1234").businessTimes(businessTimes).build();

        given(marketRepository.save(any())).will(invocation -> {
            return invocation.getArgument(0);
        });

        marketService.addMarket(market);
        assertThat(market.getId()).isEqualTo(1);
        assertThat(market.getName()).isEqualTo("해적수산");
        assertThat(market.getOwner()).isEqualTo("박해적");
        assertThat(market.getLevel()).isEqualTo(1);

    }
}