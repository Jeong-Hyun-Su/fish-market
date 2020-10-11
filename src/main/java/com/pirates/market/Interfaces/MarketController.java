package com.pirates.market.Interfaces;

import com.pirates.market.Domain.*;
import com.pirates.market.Domain.VO.DetailVO;
import com.pirates.market.Domain.VO.HolidayVO;
import com.pirates.market.Domain.VO.IdVO;
import com.pirates.market.Domain.VO.ListVO;
import com.pirates.market.Services.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
public class MarketController {
    private MarketService marketService;

    @Autowired
    public MarketController(MarketService marketService) {
        this.marketService = marketService;
    }

    @GetMapping("/detail")
    public DetailVO marketDetail(@RequestBody IdVO idVO){
        return marketService.getMarketDetail(idVO.getId());
    }


    @GetMapping("/list")
    public List<ListVO> marketList(){
        return marketService.getMarketList();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Market market) throws URISyntaxException {
        marketService.addMarket(market);
        URI loc = new URI("/market/" + market.getId());
        return ResponseEntity.created(loc).body("{}");
    }

    @PatchMapping("/holiday")
    public String holiday(@RequestBody HolidayVO holidayVO){
        System.out.println(holidayVO.getId());
        System.out.println(holidayVO.getHolidays());
        marketService.setHolidays(holidayVO.getId(), holidayVO.getHolidays());
        return "{}";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestBody IdVO idVO){
        marketService.deleteMarket(idVO.getId());
        return "{}";
    }
}
