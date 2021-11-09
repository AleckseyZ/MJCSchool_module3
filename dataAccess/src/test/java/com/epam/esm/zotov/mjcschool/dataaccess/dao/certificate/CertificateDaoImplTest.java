package com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import java.math.BigDecimal;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.DataAccessTestConfig;
import com.epam.esm.zotov.mjcschool.dataaccess.dao.tag.TagDaoJpaImpl;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;

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
@ContextConfiguration(classes = { DataAccessConfig.class, DataAccessTestConfig.class, CertificateDaoJpaImpl.class,
        TagDaoJpaImpl.class })
@Sql("classpath:/test/schema.sql")
public class CertificateDaoImplTest {
    @Autowired
    CertificateDao certificateDao;
    private Certificate testCertificate = new Certificate(null, "TEST", "Description", BigDecimal.valueOf(1), (short) 1,
            null, null, null);

    @Test
    @Transactional
    void createTest() {
        Optional<Certificate> createTestCertificate = certificateDao.save(testCertificate);
        assertEquals(createTestCertificate.get(), testCertificate);
    }

    @Test
    @Transactional
    void deleteTest() {
        certificateDao.save(testCertificate);
        boolean isSuccesful = certificateDao.delete(1L);
        assumeTrue(isSuccesful);
        Optional<Certificate> certificate = certificateDao.getById(1L);
        assumeTrue(certificate.isEmpty());
    }

    @Test
    @Transactional
    void readTest() {
        certificateDao.save(testCertificate);
        Certificate certificate = certificateDao.getById(1L).get();
        assertEquals(testCertificate.getName(), certificate.getName());
    }

    @Test
    @Transactional
    void selectiveUpdateTest() {
        certificateDao.save(testCertificate);
        Certificate updateTestCertificate = certificateDao
                .selectiveUpdate(new Certificate(1L, "CHANGED TEST", null, null, null, null, null, null)).get();
        assertEquals("CHANGED TEST", updateTestCertificate.getName());
        assertEquals("Description", updateTestCertificate.getDescription());
    }
}