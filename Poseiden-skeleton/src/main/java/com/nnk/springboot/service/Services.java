package com.nnk.springboot.service;

import java.util.Collection;
import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

public interface Services {
    public <T> List<T> castList(Class<? extends T> clazz, Collection<?> rawCollection);
    public Collection<?> getAll();
    public Object get(Integer id);
    public void post(Object objectToPost);
    public void put(Object objectToPut) throws NotFoundException;
    public void delete(Integer id);
}
