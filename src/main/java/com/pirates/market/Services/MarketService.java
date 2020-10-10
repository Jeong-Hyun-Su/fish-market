package com.pirates.market.Services;

import com.pirates.market.Domain.Holiday;
import com.pirates.market.Domain.Market;
import com.pirates.market.Domain.MarketNotFoundException;
import com.pirates.market.Domain.MarketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Market setHolidays(Integer id, List<Holiday> holidays){
        Market market = marketRepository.findById(id).orElseThrow(() -> new MarketNotFoundException(id));
        market.setHolidays(holidays);

        return market;
    }
}
