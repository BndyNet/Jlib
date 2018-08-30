/**
 * Copyright (c) 2018 BNDY-NET. All Rights Reserved.
 * http://bndy.net
 */
package net.bndy.lib.data;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Bendy Zhang
 * @version 1.0
 */
public abstract class JpaService<TEntity, TKey> {

    protected Class<TEntity> entityClass;
    
    @Autowired
    JpaRepository<TEntity, TKey> repository;
    
    @SuppressWarnings("unchecked")
    public JpaService() {
        this.entityClass = (Class<TEntity>) ((ParameterizedType) getClass()
            .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
    public List<TEntity> getAll() {
        return this.repository.findAll();
    }
    
    public  List<TEntity> getAll(Sort sort) {
        return this.repository.findAll(sort);
    }
    
    public TEntity get(TKey id) {
        Optional<TEntity> opt = this.repository.findById(id);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }
    
    public List<TEntity> get(Iterable<TKey> ids) {
        return this.repository.findAllById(ids);
    }
    
    public void delete(TKey id) {
        this.repository.deleteById(id);
    }
    
    public TEntity save(TEntity entity) {
        return this.repository.saveAndFlush(entity);
    }
    
    public List<TEntity> saveAll(Iterable<TEntity> entities) {
        return this.repository.saveAll(entities);
    }

    public Page<TEntity> findAll(Pageable pageable) {
        return this.repository.findAll(pageable);
    }
}
