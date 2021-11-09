package com.epam.esm.zotov.mjcschool.service;

/**
 * Defines methods related to deleting objects
 */
public interface DeleteService {
    /**
     * Deletes object with specified id. Returns boolean indicating whether deletion
     * is successful or not.
     * 
     * @param object - object to be deleted.
     * @return <code>true</code> if object successfuly deleted.
     */
    boolean delete(long id);
}