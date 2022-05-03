package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service("tradeService")
public class TradeService implements Services {

    private static final Logger logger = LogManager.getLogger("tradeService");

    @Autowired
    protected TradeRepository tradeRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<Trade> trade = tradeRepo.findAll();
        return trade;
    }

    @Override
    public Object get(Integer id) {
        Trade trade = tradeRepo.findById(id).orElse(null);
        return trade;
    }

    @Override
    public void post(Object objectToPost) {
        Trade trade = new Trade(objectToPost);
        tradeRepo.saveAndFlush(trade);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        Trade tradeToPut = new Trade(objectToPut);
        Trade ancientTrade = tradeRepo.findById(tradeToPut.getTradeId()).orElseThrow(NotFoundException::new);
        ancientTrade.setAccount(tradeToPut.getAccount());
        ancientTrade.setType(tradeToPut.getType());
        ancientTrade.setBuyQuantity(tradeToPut.getBuyQuantity());
        tradeRepo.saveAndFlush(ancientTrade);
    }

    @Override
    public void delete(Integer id) {
        tradeRepo.deleteById(id);
    }


    
}
