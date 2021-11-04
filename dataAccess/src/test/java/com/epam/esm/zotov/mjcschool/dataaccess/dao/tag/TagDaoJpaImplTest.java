package com.epam.esm.zotov.mjcschool.dataaccess.dao.tag;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Tag;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { DataAccessConfig.class, DataAccessTestConfig.class, TagDaoJpaImpl.class })
@Sql("classpath:/test/schema.sql")
public class TagDaoJpaImplTest {
    @Autowired
    TagDao tagDao;
    private Tag testTag = new Tag(null, "TEST");

    @Test
    @Transactional
    void createTest() {
        Optional<Tag> createTestTag = tagDao.save(testTag);
        assertEquals(createTestTag.get(), testTag);
    }

    @Test
    @Transactional
    void readTest() {
        tagDao.save(testTag);
        Tag tag = tagDao.getById(1L).get();
        assertEquals(testTag.getName(), tag.getName());
    }

    @Test
    @Transactional
    void deleteTest() {
        tagDao.save(testTag);
        boolean isSuccesful = tagDao.delete(1L);
        assumeTrue(isSuccesful);
        Optional<Tag> tag = tagDao.getById(1L);
        assumeTrue(tag.isEmpty());
    }
}