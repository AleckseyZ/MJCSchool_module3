package com.epam.esm.zotov.mjcschool.service;

import java.util.Optional;

/**
 * Defines service methods related to creating new objects of type
 * <code>T</code>
 */
public interface CreateService<T> {
    /**
     * Saves <code>T</code> object. Returns boolean indicating whether saving is
     * successful or not.
     * 
     * @param object - object to be saved.
     * @return <code>true</code> if object successfuly saved.
     */
    Optional<T> save(T object);

}