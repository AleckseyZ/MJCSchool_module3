package com.epam.esm.zotov.mjcschool.service;

import java.util.List;
import java.util.Optional;

/**
 * Defines create, read methods.
 */
public interface ReadService<T> {
    /**
     * Gets <code>T</code> type object with specified id and wraps it in the
     * <code>Optional</code>.
     * 
     * @param id - id of desired object.
     * @return <code>Optional</code> containing object with specified id.
     */
    Optional<T> getById(long id);

    /**
     * Gets <code>List</code> of all objects with type <code>T</code>.
     * 
     * @return <code>List</code> of all <code>T</code> objects.
     */
    List<T> getAll();

    /**
     * Gets <code>List</code> containing up to limit number of objects with type
     * <code>T</code> and id greater than afterId.
     * 
     * @param limit   - max amount of objects to return
     * 
     * @param afterId - qualificator for objects
     * @return <code>List</code> of <code>T</code> type objects containing up to
     *         limit number of objects.
     */
    List<T> getPage(int limit, long afterId);
}