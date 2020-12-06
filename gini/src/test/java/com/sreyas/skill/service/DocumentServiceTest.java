package com.sreyas.skill.service;

import com.sreyas.skill.model.Document;
import com.sreyas.skill.model.UpdateDocument;
import com.sreyas.skill.repository.DocumentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DocumentServiceTest {

    @InjectMocks
    DocumentService service;

    @Mock
    DocumentRepository repository;

    private void save(Document document) {
        when(repository.save(document))
                .thenReturn(document);
        service.save(document);
        assertEquals(10, document.getId());
    }

    @Test
    void savePNG() {
        Document document = Document.builder().userid("john").type("png").description("Bill of Fuel").id(10).build();
        save(document);

    }

    @Test
    void savePDF() {
        Document document = Document.builder().userid("john").type("pdf").description("Bill of Fuel").id(10).build();
        save(document);

    }

    @Test
    void saveJPG() {
        Document document = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        save(document);

    }

    @Test
    void createWithUnknownType() {
        Document document = Document.builder().userid("john").type("doc").description("Bill of Fuel").id(10).build();
        when(repository.save(document))
                .thenThrow(new ConstraintViolationException(Collections.emptySet()));
        assertThrows(ConstraintViolationException.class, () ->
                service.save(document));

    }

    @Test
    void createWithInvalidDescription() {
        Document document = Document.builder().userid("john").type("png").description("Bill").id(10).build();
        when(repository.save(document))
                .thenThrow(new ConstraintViolationException(Collections.emptySet()));
        assertThrows(ConstraintViolationException.class, () ->
                service.save(document));

    }

    @Test
    void saveWithNullUid() {
        Document document = Document.builder().type("png").description("Bill of Fuel").id(10).build();
        when(repository.save(document))
                .thenThrow(new ConstraintViolationException(Collections.emptySet()));
        assertThrows(ConstraintViolationException.class, () ->
                service.save(document));
    }

    @Test
    void createWithInvalidUserId() {
        Document document = Document.builder().userid("john").description("Bill of Fuel").id(10).build();
        when(repository.save(document))
                .thenThrow(new ConstraintViolationException(Collections.emptySet()));
        assertThrows(ConstraintViolationException.class, () ->
                service.save(document));

    }

    @Test
    void update() {
        final String newDesc = "Bill of Internet";
        Document document = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        when(repository.findById(10L))
                .thenReturn(java.util.Optional.ofNullable(document));
        assert document != null;
        document.setDescription(newDesc);
        when(repository.save(document))
                .thenReturn(document);
        String description = service.update(10L, new UpdateDocument(newDesc));
        assertEquals(newDesc, description);

    }

    @Test
    void updateWithWrongDesc() {
        final String newDesc = "Bill";
        Document document = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        when(repository.findById(10L))
                .thenReturn(java.util.Optional.ofNullable(document));
        assert document != null;
        document.setDescription(newDesc);
        when(repository.save(document))
                .thenThrow(new ConstraintViolationException(Collections.emptySet()));
        assertThrows(ConstraintViolationException.class, () ->
                service.update(10L, new UpdateDocument(newDesc)));
    }

    @Test
    void get() {
        Document document = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        when(repository.findByUseridAndId("john", 10))
                .thenReturn(java.util.Optional.ofNullable(document));
        Document doc = service.findByUserIdAndId("john", 10);
        assertEquals(10, doc.getId());


    }

    @Test
    void getUnknown() {
        // Document document = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        when(repository.findByUseridAndId("john", 2))
                .thenReturn(Optional.empty());
        Document doc = service.findByUserIdAndId("john", 2);
        assertEquals(null, doc);


    }

    @Test
    void getList() {
        Document document1 = Document.builder().userid("john").type("jpg").description("Bill of Fuel").id(10).build();
        Document document2 = Document.builder().userid("john").type("jpg").description("Bill of Phone").id(11).build();
        when(repository.findByUserid("john"))
                .thenReturn(java.util.Optional.ofNullable(Arrays.asList(document1, document2)));
        List<Document> doc = service.findByUserId("john");
        assertEquals(2, doc.size());

    }

    @Test
    void getUnknownUserList() {
        when(repository.findByUserid("john"))
                .thenReturn(Optional.of(Collections.EMPTY_LIST));
        List<Document> doc = service.findByUserId("john");
        assertTrue(doc.isEmpty());


    }

}