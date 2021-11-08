package com.epam.esm.zotov.mjcschool.dataaccess.dao;

/**
 * Defines data access deletes methods
 * 
 */
public interface DeleteDao {
    /**
     * Removes object with matching id from the data source. Returns boolean
     * represntig whether deletion is successful or not.
     * 
     * @param id - id of an object to be removed
     * @return <code>true</code> if object is successfuly removed
     */
    boolean delete(long id);
}