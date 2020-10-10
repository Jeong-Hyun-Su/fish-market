package com.pirates.market.Domain;

public class MarketNotFoundException extends RuntimeException{
    public MarketNotFoundException(Integer id){
        super("Could not find Market ID: " + id);
    }
}
