package com.epam.esm.zotov.mjcschool.dataaccess.dao.tag;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.CreateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.DeleteDao;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.ReadDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

/**
 * Defines data source manipulating methods for the Tag type
 * 
 * @see CreateDao
 */
public interface TagDao extends CreateDao<Tag>, ReadDao<Tag>, DeleteDao {
    /**
     * Queries data source for an object with specified tag name and wraps result in
     * a <code>Optiona.ofNullable()</code>.
     * 
     * @param name - name of a desired tag.
     * @return <code>Optional</code> result of query.
     */
    Optional<Tag> getByName(String name);

    public Optional<Tag> findFavoriteTagOfMostSpendingUser();
}