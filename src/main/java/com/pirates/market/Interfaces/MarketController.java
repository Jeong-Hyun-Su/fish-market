package com.pirates.market.Interfaces;

import com.pirates.market.Domain.Market;
import com.pirates.market.Services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class MarketController {
    private MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Market market) throws URISyntaxException {
        marketService.addMarket(market);
        URI loc = new URI("/market/" + market.getId());
        return ResponseEntity.created(loc).body("{}");
    }
}
