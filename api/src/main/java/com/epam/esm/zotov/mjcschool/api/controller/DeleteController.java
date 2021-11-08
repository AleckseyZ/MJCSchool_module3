package com.epam.esm.zotov.mjcschool.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * Interface that defines basic delete RESTful methods.
 */
public interface DeleteController {
    /**
     * Deletes object with a specified id.
     * 
     * @param targetId id of an object to be deleted.
     * @return <code>true</code> if deletion was successful.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{targetId}", method = RequestMethod.DELETE)
    void delete(@PathVariable long targetId);
}