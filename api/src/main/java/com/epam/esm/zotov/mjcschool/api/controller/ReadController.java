package com.epam.esm.zotov.mjcschool.api.controller;

import com.epam.esm.zotov.mjcschool.api.dto.ListDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * Interface that defines basic read RESTful methods.
 * <p>
 * * @param T - type of the objects that controller works with.
 */
public interface ReadController<T> {
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
    ListDto<T> getPage(@RequestParam int limit, @RequestParam long afterId);

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
}