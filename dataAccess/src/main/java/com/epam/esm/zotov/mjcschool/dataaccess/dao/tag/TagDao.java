package com.epam.esm.zotov.mjcschool.dataaccess.dao.tag;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.CrdDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

/**
 * Defines data source manipulating methods for the Tag type
 * 
 * @see CrdDao
 */
public interface TagDao extends CrdDao<Tag> {
    /**
     * Queries data source for an object with specified tag name and wraps result in
     * a <code>Optiona.ofNullable()</code>.
     * 
     * @param name - name of a desired tag.
     * @return <code>Optional</code> result of query.
     */
    Optional<Tag> getByName(String name);
}