package com.sreyas.skill.service;

import com.sreyas.skill.exception.DocumentNotFoundException;
import com.sreyas.skill.model.Document;
import com.sreyas.skill.model.UpdateDocument;
import com.sreyas.skill.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * Author : Sreyas V Pariyath
 * Date   : 03/12/20
 * Time   : 7:54 PM
 */
@Service
public class DocumentService {

    private final DocumentRepository repository;

    public DocumentService(DocumentRepository repository) {
        this.repository = repository;
    }

    /**
     * Saving the documents to the database
     *
     * @param document
     * @return
     */
    public long save(Document document) {
        return repository.save(document).getId();
    }

    /**
     * The description of a saved document can be updated, if the given id is not present then
     * a DocumentNotFoundException will be thrown
     *
     * @param id
     * @param updateDocument
     * @return
     */
    public String update(long id, UpdateDocument updateDocument) {
        Document document = repository.findById(id)
                .orElseThrow(() -> new DocumentNotFoundException());
        document.setDescription(updateDocument.getDescription());
        return repository.save(document).getDescription();

    }

    /**
     * Fetch a document with id and userid
     *
     * @param userid
     * @param id
     * @return
     */
    public Document findByUserIdAndId(String userid, long id) {
        return repository.findByUseridAndId(userid, id).orElse(null);
    }

    /**
     * Fetch all documents of a given user, empty list will be returned if data is not available
     *
     * @param userid
     * @return
     */
    public List<Document> findByUserId(String userid) {
        return repository.findByUserid(userid).orElse(Collections.emptyList());
    }
}
