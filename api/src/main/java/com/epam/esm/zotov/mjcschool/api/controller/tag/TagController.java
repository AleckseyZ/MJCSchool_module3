package com.epam.esm.zotov.mjcschool.api.controller.tag;

import com.epam.esm.zotov.mjcschool.api.controller.CreateController;
import com.epam.esm.zotov.mjcschool.api.controller.DeleteController;
import com.epam.esm.zotov.mjcschool.api.controller.ReadController;
import com.epam.esm.zotov.mjcschool.api.dto.TagDto;

/**
 * Defines RESTful methods for the <code>Tag</code> type.
 * <p>
 * 
 * @see ReadController
 */
public interface TagController extends ReadController<TagDto>, CreateController<TagDto>, DeleteController {
    public TagDto findFavoriteTagOfMostSpendingUser();
}