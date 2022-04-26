package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service("curvePointService")
public class CurvePointService implements Services {

    private static final Logger logger = LogManager.getLogger("CurvePointService");

    @Autowired
    protected CurvePointRepository curvePointRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<CurvePoint> curvePoint = curvePointRepo.findAll();
        return curvePoint;
    }

    @Override
    public Object get(Integer id) {
        CurvePoint curvePoint = curvePointRepo.findById(id).orElse(null);
        return curvePoint;
    }

    @Override
    public void post(Object objectToPost) {
        CurvePoint curvePointToPost = new CurvePoint(objectToPost);
        curvePointRepo.saveAndFlush(curvePointToPost);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        CurvePoint curvePointToPut = new CurvePoint(objectToPut);
        CurvePoint ancientCurvePoint = curvePointRepo.findById(curvePointToPut.getId()).orElseThrow(NotFoundException::new);
        ancientCurvePoint.setCurveId(curvePointToPut.getCurveId());
        ancientCurvePoint.setTerm(curvePointToPut.getTerm());
        ancientCurvePoint.setValue(curvePointToPut.getValue());
        curvePointRepo.saveAndFlush(ancientCurvePoint);
    }

    @Override
    public void delete(Integer id) {
        curvePointRepo.deleteById(id);
    }
    
}
