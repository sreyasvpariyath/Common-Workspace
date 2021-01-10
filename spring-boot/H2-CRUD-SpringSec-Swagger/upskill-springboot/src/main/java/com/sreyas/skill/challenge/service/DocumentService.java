package com.sreyas.skill.challenge.service;

import com.sreyas.skill.challenge.repository.DocumentRepository;
import lombok.extern.slf4j.Slf4j;
import com.sreyas.skill.challenge.exception.AdminServiceException;
import com.sreyas.skill.challenge.exception.DocumentNotFoundException;
import com.sreyas.skill.challenge.exception.FileUploadException;
import com.sreyas.skill.challenge.model.Document;
import com.sreyas.skill.challenge.model.UpdateDocument;
import com.sreyas.skill.challenge.utils.FileServiceUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Author : Sreyas V Pariyath Date : 03/12/20 Time : 7:54 PM
 */
@Slf4j
@Service
public class DocumentService {

    private final DocumentRepository repository;

    private final FileServiceUtils fileServiceUtils;

    public DocumentService(DocumentRepository repository, FileServiceUtils fileServiceUtils) {
        this.repository = repository;
        this.fileServiceUtils = fileServiceUtils;
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
     * The description of a saved document can be updated, if the given id is not
     * present then a DocumentNotFoundException will be thrown
     *
     * @param id
     * @param updateDocument
     * @return
     */
    public void update(long id, UpdateDocument updateDocument) throws RuntimeException {
        Document document = repository.findById(id).orElseThrow(DocumentNotFoundException::new);
        document.setDescription(updateDocument.getDescription());
        repository.save(document);
        log.info("Document description has been updated {}..to", document.getDescription());
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
     * Fetch all documents of a given user, empty list will be returned if data is
     * not available
     *
     * @param userid
     * @return
     */
    public List<Document> findByUserId(String userid) {
        return repository.findByUserid(userid);
    }

    /**
     * Zips and upload to admin portal
     *
     * @param userid
     * @return number of records
     */
    public long upload(String userid) {
        List<Document> documents = findByUserId(userid);
        if (documents.isEmpty())
            return 0;
        Path tempFolder = null;
        try {
            File directory = new File("" + System.currentTimeMillis());
            boolean mkdir = directory.mkdir();
            log.info("[DS] Temp folder creation status {}..file path {}", mkdir, directory.getAbsolutePath());
            if (!mkdir) {
                log.error("[DS] Folder creation failed");
                throw new FileUploadException();
            }
            tempFolder = Paths.get(directory.getAbsolutePath());
            for (Document document : documents) {
                File file = new File(tempFolder + "/" + document.getId() + ".json");
                Files.write(Paths.get(file.getPath()), document.toString().getBytes());
            }
            fileServiceUtils.upload(tempFolder.toFile());
        } catch (FileUploadException | AdminServiceException e) {
            log.error("[DS] Error while uploading the file", e);
            throw e;
        } catch (Exception e) {
            log.error("[DS] Error while uploading the file", e);
            throw new FileUploadException();
        } finally {
            if (tempFolder != null) {
                fileServiceUtils.delete(tempFolder.toFile());
                Path zipFolderPath = Paths.get(tempFolder.toFile().getAbsolutePath().concat(".zip"));
                fileServiceUtils.delete(zipFolderPath.toFile());
            }
        }
        return documents.size();
    }
}
