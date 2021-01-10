package com.sreyas.skill.challenge.controller;

import com.sreyas.skill.challenge.model.ApiResponse;
import com.sreyas.skill.challenge.model.Document;
import com.sreyas.skill.challenge.model.UpdateDocument;
import com.sreyas.skill.challenge.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * All the incoming requests will be handled in this class
 * Author : Sreyas V Pariyath
 * Date   : 04/12/20
 * Time   : 2:12 PM
 */
@RestController
@RequestMapping("v1/documents")
@SuppressWarnings("rawtypes")
public class DocumentController {

    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    /**
     * If the input is having a valid document, then it will be saved to the in memory database
     *
     * @param document
     * @return
     */
    @PostMapping
    public ResponseEntity<ApiResponse> save(@RequestBody @Valid Document document) {
        final long id = documentService.save(document);
        ApiResponse apiResponse = ApiResponse.builder().data(id).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * A document can be fetched, using userid and id
     *
     * @param userid
     * @param id
     * @return
     */
    @GetMapping("/{userid}/{id}")
    public ResponseEntity getByUserIdAndId(@PathVariable String userid, @PathVariable long id) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(documentService.findByUserIdAndId(userid, id)).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * A list of documents of the given user id will be returned
     *
     * @param userid
     * @return
     */
    @GetMapping("/{userid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity getByUserId(@PathVariable String userid) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(documentService.findByUserId(userid)).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * A list of documents of the given user id will be returned
     *
     * @param userid
     * @return
     */
    @GetMapping("/upload/{userid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity upload(@PathVariable String userid) {
        long result = documentService.upload(userid);
        final String message = result == 0 ? "No documents to upload" : result + " documents uploaded";
        ApiResponse apiResponse = ApiResponse.builder().message(message).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * The description of a saved document can be updated using its id
     * Note: Minimum length of description should be 5
     *
     * @param id
     * @param updateDocument
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody @Valid UpdateDocument updateDocument) {
        documentService.update(id, updateDocument);
        return ResponseEntity.ok().build();
    }
}
