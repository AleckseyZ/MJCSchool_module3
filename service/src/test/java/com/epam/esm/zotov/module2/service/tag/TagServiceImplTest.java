package com.epam.esm.zotov.module2.service.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.tag.TagDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;
import com.epam.esm.zotov.mjcschool.service.tag.TagServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TagServiceImplTest {
    @InjectMocks
    private TagServiceImpl tagService;
    @Mock
    private TagDao tagDao;
    private Tag testTag = new Tag(1L, "TEST");

    @Test
    void getAllTest() {
        List<Tag> list = new ArrayList<>();
        list.add(testTag);
        Mockito.when(tagDao.getAll()).thenReturn(list);

        assertEquals(testTag, tagService.getAll().get(0));
        verify(tagDao, times(1)).getAll();
    }

    @Test
    void getByIdTest() {
        Optional<Tag> optional = Optional.of(testTag);
        Mockito.when(tagDao.getById(0)).thenReturn(optional);

        assertEquals("TEST", tagService.getById(0).get().getName());
        verify(tagDao, times(1)).getById(0);
    }

    @Test
    void saveTest() {
        Mockito.when(tagDao.save(testTag)).thenReturn(Optional.of(testTag));

        assertEquals(tagService.save(testTag).get(), testTag);
        verify(tagDao, times(1)).save(testTag);
    }

    @Test
    void deleteTest() {
        Mockito.when(tagDao.delete(1L)).thenReturn(true);

        assertTrue(tagService.delete(1L));
        verify(tagDao, times(1)).delete(1L);
    }
}