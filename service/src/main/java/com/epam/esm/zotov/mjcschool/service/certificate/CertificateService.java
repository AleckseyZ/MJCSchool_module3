package com.epam.esm.zotov.mjcschool.service.certificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;
import com.epam.esm.zotov.mjcschool.service.CreateService;
import com.epam.esm.zotov.mjcschool.service.DeleteService;
import com.epam.esm.zotov.mjcschool.service.ReadService;

/**
 * Defines service methods operating with <code>Certificate</code> objects
 * 
 * @see CrdService
 */
public interface CertificateService extends ReadService<Certificate>, CreateService<Certificate>, DeleteService {
    /**
     * Updates <code>Certificate</code> modified fields using value from passed
     * <code>updatedCertificate</code>.
     * 
     * @param updatedCertificate <code>Certificate</code> with modified fields.
     * @return <code>ture</code> if update is successful.
     */
    Optional<Certificate> selectiveUpdate(Certificate updatedCertificate);

    /**
     * Searches for the <code>Certificate</code> objects fitting passed in the
     * <code>searchParams</code> criteria.
     * 
     * @param searchParams - <code>Map</code> of search parameter names and their
     *                     corresponding values.
     * @return <code>List</code> of <code>Certificate</code> objects matching search
     *         criteria.
     */
    List<Certificate> search(Map<String, String> searchParams);
}