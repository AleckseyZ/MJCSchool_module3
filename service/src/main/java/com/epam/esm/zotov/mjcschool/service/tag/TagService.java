package com.epam.esm.zotov.mjcschool.service.tag;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.service.CreateService;
import com.epam.esm.zotov.mjcschool.service.DeleteService;
import com.epam.esm.zotov.mjcschool.service.ReadService;

/**
 * Defines service methods operating with <code>Tag</code> objects
 * 
 * @see CrdService
 */
public interface TagService extends ReadService<Tag>, CreateService<Tag>, DeleteService {
}