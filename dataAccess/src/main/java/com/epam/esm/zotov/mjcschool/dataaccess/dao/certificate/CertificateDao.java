package com.epam.esm.zotov.mjcschool.dataaccess.dao.certificate;

import java.util.Optional;

import com.epam.esm.zotov.mjcschool.dataaccess.dao.CrdDao;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;

/**
 * Defines data source manipulating methods for the Tag type
 * 
 * @see CrdDao
 */
public interface CertificateDao extends CrdDao<Certificate> {
    /**
     * Updates <code>Certificate</code> in the data source. Will only update
     * modified fields.
     * 
     * @param updatedCertificate - <code>Certificate</code> with updated fields.
     * @return <code>true</code> if update is successful.
     */
    Optional<Certificate> selectiveUpdate(Certificate updatedCertificate);
}