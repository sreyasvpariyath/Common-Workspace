package com.sreyas.skill.controller;

import com.sreyas.skill.model.ApiResponse;
import com.sreyas.skill.model.Document;
import com.sreyas.skill.model.UpdateDocument;
import com.sreyas.skill.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * All the incoming requests will be handled in this class
 * Author : Sreyas V Pariyath
 * Date   : 04/12/20
 * Time   : 2:12 PM
 */
@RestController
@RequestMapping("v1/documents")
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
    public ResponseEntity save(@RequestBody Document document) {
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
     * @param userid
     * @return
     */
    @GetMapping("/{userid}")
    public ResponseEntity getByUserId(@PathVariable String userid) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(documentService.findByUserId(userid)).build();
        return ResponseEntity.ok().body(apiResponse);
    }

    /**
     * The description of a saved document can be updated using its id
     * Note: Minimum length of description should be 5
     * @param id
     * @param updateDocument
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable long id, @RequestBody UpdateDocument updateDocument) {
        ApiResponse apiResponse = ApiResponse.builder()
                .data(documentService.update(id, updateDocument)).build();
        return ResponseEntity.ok().body(apiResponse);
    }
}
