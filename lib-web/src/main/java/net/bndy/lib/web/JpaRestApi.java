/**
 * Copyright (c) 2018 BNDY-NET. All Rights Reserved.
 * http://bndy.net
 */
package net.bndy.lib.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.bndy.lib.data.JpaService;

/*
 * RESTful API controller with CRUD methods
 */
public abstract class JpaRestApi<TEntity, TKey> {
    
    @Autowired
    private JpaService<TEntity, TKey> jpaService;

    @RequestMapping(method = RequestMethod.GET)
    public List<TEntity> get() {
        return this.jpaService.getAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<TEntity> get(@PageableDefault(value = 10) Pageable pageable) {
        return this.jpaService.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public TEntity get(@PathVariable(name = "id") TKey id) {
        return this.jpaService.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public TEntity put(@PathVariable(name = "id") TKey id, @RequestBody TEntity entity) {
        return this.jpaService.save(entity);
    }

    @RequestMapping(method = RequestMethod.POST)
    public TEntity post(@RequestBody TEntity entity) {
        return this.jpaService.save(entity);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id") TKey id) {
        this.jpaService.delete(id);
    }
}
