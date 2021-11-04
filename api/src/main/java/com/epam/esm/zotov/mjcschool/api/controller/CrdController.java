package com.epam.esm.zotov.mjcschool.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Interface that defines basic create, read and delete RESTful methods.
 * <p>
 * * @param T - type of the objects that controller works with.
 */
public interface CrdController<T> {
    /**
     * Gets up to the limit number of objects with type <code>T</code> and id
     * greater than afterId. Might return an error message if no suitable objects
     * found.
     * 
     * @param limit   - max amount of objects to return
     * 
     * @param afterId - qualificator for objects
     * @return <code>List</code> of <code>T</code> type objects containing up to
     *         limit number of objects.
     */
    @GetMapping
    List<T> getPage(@RequestParam int limit, @RequestParam(required = false) long afterId);

    /**
     * Gets an object with a specified id. Might return an error message if no
     * object with specified id found.
     * 
     * @param targetId id of desired object.
     * @return object with a specified id or an error message.
     * 
     */
    @RequestMapping(value = "/{targetId}", method = RequestMethod.GET)
    T getById(@PathVariable long targetId);

    /**
     * Deletes object with a specified id.
     * 
     * @param targetId id of an object to be deleted.
     * @return <code>true</code> if deletion was successful.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{targetId}", method = RequestMethod.DELETE)
    void delete(@PathVariable long targetId);

    /**
     * Saves object of <code>T</code> type passed in the body.
     * 
     * @param object object to be saved
     * @return <code>true</code> if object was successfuly saved
     */
    @PostMapping
    T save(@RequestBody T object);
}