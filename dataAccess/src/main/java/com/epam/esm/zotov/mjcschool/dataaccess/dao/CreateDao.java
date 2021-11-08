package com.epam.esm.zotov.mjcschool.dataaccess.dao;

import java.util.Optional;

/**
 * Defines data access save methods for <code>T</code> type object
 * 
 * @param T - type of object
 */
public interface CreateDao<T> {
    /**
     * Inserts object into data source using values from recived object. Returns
     * boolean represntig whether addition is successful or not.
     * 
     * @param object - object to be added to the data source
     * @return <code>true</code> if object is successfuly added to the datasource
     */
    Optional<T> save(T object);
}