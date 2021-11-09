package com.epam.esm.zotov.module2.service.certificate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate.CertificateDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.service.certificate.CertificateServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CertificateServiceImplTest {
    @InjectMocks
    CertificateServiceImpl certificateService;
    @Mock
    CertificateDao certificateDao;
    Certificate testCertificate = new Certificate(1L, "Test cert", null, null, null, null, null, null);

    @Test
    void getAllTest() {
        List<Certificate> list = new ArrayList<>();
        list.add(testCertificate);
        Mockito.when(certificateDao.getAll()).thenReturn(list);

        assertEquals(testCertificate, certificateService.getAll().get(0));
        verify(certificateDao, times(1)).getAll();
    }

    @Test
    void getByIdTest() {
        Optional<Certificate> optional = Optional.of(testCertificate);
        Mockito.when(certificateDao.getById(0)).thenReturn(optional);

        assertEquals(1L, certificateService.getById(0).get().getCertificateId());
        verify(certificateDao, times(1)).getById(0);
    }

    @Test
    void saveTest() {
        Mockito.when(certificateDao.save(testCertificate)).thenReturn(Optional.of(testCertificate));

        assertEquals(certificateService.save(testCertificate).get(), testCertificate);
        verify(certificateDao, times(1)).save(testCertificate);
    }

    @Test
    void deleteTest() {
        Mockito.when(certificateDao.delete(1L)).thenReturn(true);

        assertTrue(certificateService.delete(1L));
        verify(certificateDao, times(1)).delete(1L);
    }

    @Test
    void selectiveUpdateTest() {
        Mockito.when(certificateDao.selectiveUpdate(testCertificate))
                .thenReturn(Optional.of(new Certificate(null, "New name", null, null, null, null, null, null)));

        assertEquals(testCertificate.getName(), "Test cert");
        assertEquals(certificateService.selectiveUpdate(testCertificate).get().getName(), "New name");
        verify(certificateDao, times(1)).selectiveUpdate(testCertificate);
    }
}