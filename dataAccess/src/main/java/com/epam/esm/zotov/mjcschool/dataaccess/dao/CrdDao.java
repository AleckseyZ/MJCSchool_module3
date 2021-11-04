package com.epam.esm.zotov.mjcschool.dataaccess.dao;

import java.util.List;
import java.util.Optional;

/**
 * Defines basic create, read nad delete data access methods for <code>T</code>
 * type object
 * 
 * @param T - type of object
 */
public interface CrdDao<T> {
    /**
     * Queries data source for an object with specified id and wraps result in a
     * <code>Optiona.ofNullable()</code>
     * 
     * @param id - id of desired object.
     * @return <code>Optional</code> result of query.
     */
    Optional<T> getById(long id);

    /**
     * Queries data source for the list of all objects of type <code>T</code>.
     * 
     * @return <code>List</code> of all <code>T</code> type objects.
     */
    List<T> getAll();

    /**
     * Queries data source for the list containing up to limit number of type
     * <code>T</code> objects having id greater than afterId.
     * 
     * @param limit   - max amount of objects to return
     * 
     * @param afterId - qualificator for objects
     * 
     * @return <code>List</code> of <code>T</code> type objects containing up to
     *         limit number of objects.
     */
    List<T> getPage(int limit, long afterId);

    /**
     * Inserts object into data source using values from recived object. Returns
     * boolean represntig whether addition is successful or not.
     * 
     * @param object - object to be added to the data source
     * @return <code>true</code> if object is successfuly added to the datasource
     */
    Optional<T> save(T object);

    /**
     * Removes object with matching id from the data source. Returns boolean
     * represntig whether deletion is successful or not.
     * 
     * @param id - id of an object to be removed
     * @return <code>true</code> if object is successfuly removed
     */
    boolean delete(long id);
}