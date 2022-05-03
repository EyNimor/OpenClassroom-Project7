package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

@Service("ruleNameService")
public class RuleNameService implements Services {

    private static final Logger logger = LogManager.getLogger("RuleNameService");

    @Autowired
    protected RuleNameRepository ruleNameRepo;

    @Override
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection) {
        return rawCollection.stream().map(o -> clazz.cast(o)).collect(Collectors.toList());
    }

    @Override
    public Collection<?> getAll() {
        List<RuleName> ruleName = ruleNameRepo.findAll();
        return ruleName;
    }

    @Override
    public Object get(Integer id) {
        RuleName ruleName = ruleNameRepo.findById(id).orElse(null);
        return ruleName;
    }

    @Override
    public void post(Object objectToPost) {
        RuleName ruleName = new RuleName(objectToPost);
        ruleNameRepo.saveAndFlush(ruleName);
    }

    @Override
    public void put(Object objectToPut) throws NotFoundException {
        RuleName ruleNameToPut = new RuleName(objectToPut);
        RuleName ancientRuleName = ruleNameRepo.findById(ruleNameToPut.getId()).orElseThrow(NotFoundException::new);
        ancientRuleName.setName(ruleNameToPut.getName());
        ancientRuleName.setDescription(ruleNameToPut.getDescription());
        ruleNameRepo.saveAndFlush(ancientRuleName);
    }

    @Override
    public void delete(Integer id) {
        ruleNameRepo.deleteById(id);
    }
    
}
