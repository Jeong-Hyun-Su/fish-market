package com.pirates.market.Domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MarketRepository extends CrudRepository<Market, Integer> {
    List<Market> findAll();
    Optional<Market> findById(Integer id);
    Market save(Market market);
}
