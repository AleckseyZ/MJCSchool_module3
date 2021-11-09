package com.epam.esm.zotov.mjcschool.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Interface that defines basic createRESTful methods.
 * <p>
 * * @param T - type of the objects that controller works with.
 */
public interface CreateController<T> {
    /**
     * Saves object of <code>T</code> type passed in the body.
     * 
     * @param object object to be saved
     * @return <code>true</code> if object was successfuly saved
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    T save(@RequestBody T object);
}