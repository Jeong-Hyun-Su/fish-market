package com.pirates.market.Domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MarketRepository extends CrudRepository<Market, Integer> {
//    List<Market> findAllById(Integer Id);
    Market save(Market market);
}
