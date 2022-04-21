package com.nnk.springboot.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service("bidService")
public class BidService implements Services {
    
    private static final Logger logger = LogManager.getLogger("BidService");

    @Autowired
    protected BidListRepository bidListRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<BidList> bidList = bidListRepo.findAll();
        return bidList;
    }

    @Override
    public Object get(Integer id) {
        BidList bid = bidListRepo.findById(id).orElse(null);
        return bid;
    }

    @Override
    public void post(Object objectToPost) {
        BidList bidToPost = new BidList(objectToPost);
        bidListRepo.saveAndFlush(bidToPost);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        BidList bidToPut = (BidList) objectToPut;
        BidList ancientBid = bidListRepo.findById(bidToPut.getBidListId()).orElseThrow(NotFoundException::new);
        ancientBid.setAccount(bidToPut.getAccount());
        ancientBid.setType(bidToPut.getType());
        ancientBid.setBidQuantity(bidToPut.getBidQuantity());
        bidListRepo.saveAndFlush(ancientBid);
    }

    @Override
    public void delete(Integer id) {
        bidListRepo.deleteById(id);
    }

}
