package com.pirates.market.Services;

import com.pirates.market.Domain.Holiday;
import com.pirates.market.Domain.Market;
import com.pirates.market.Domain.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketService {
    private MarketRepository marketRepository;

    @Autowired
    public MarketService(MarketRepository marketRepository) {
        this.marketRepository = marketRepository;
    }

    public Market addMarket(Market market){
        return marketRepository.save(market);
    }

}
