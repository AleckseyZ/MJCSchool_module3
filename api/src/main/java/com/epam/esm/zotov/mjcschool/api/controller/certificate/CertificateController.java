package com.epam.esm.zotov.mjcschool.api.controller.certificate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.epam.esm.zotov.mjcschool.api.controller.CrdController;
import com.epam.esm.zotov.mjcschool.dataaccess.model.Certificate;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Defines RESTful methods for the <code>Certificate</code> type.
 * <p>
 * 
 * @see CrdController
 */
public interface CertificateController extends CrdController<Certificate> {
    /**
     * Searches the certificates that fit desired criteria. If thereis no such
     * certificates, might return an error.
     * 
     * @param searchParams <code>Map</code> of search parameters and their values.
     * @return <code>List</code> of fitting certificates.
     */
    @RequestMapping(method = RequestMethod.GET, params = { "tagName", "name", "description", "sortByDate", "sortByName",
            "ASC", "DESC" })
    List<Certificate> search(@RequestParam Map<String, String> searchParams);

    /**
     * Updates modifyed fields of <code>Certificate</code> in the data source with
     * values from passed <code>updatedCertificate</code>.
     * 
     * @param updatedCertificate updated object. Must contain id. Unupdated fields
     *                           might be <code>null</code>
     * @return <code>true</code> if object was successfuly updated
     */
    @PatchMapping(value = "/{targetId}")
    Optional<Certificate> selectiveUpdate(@PathVariable long targetId, @RequestBody Certificate updatedCertificate);
}