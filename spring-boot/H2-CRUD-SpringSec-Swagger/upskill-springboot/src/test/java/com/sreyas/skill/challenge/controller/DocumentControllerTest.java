package com.sreyas.skill.challenge.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sreyas.skill.challenge.constants.Constants;
import com.sreyas.skill.challenge.model.ApiResponse;
import com.sreyas.skill.challenge.model.Document;
import com.sreyas.skill.challenge.model.UpdateDocument;
import com.sreyas.skill.challenge.service.DocumentService;
import com.sreyas.skill.challenge.config.ApplicationProperties;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.validation.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DocumentController.class)
@AutoConfigureMockMvc(addFilters = false)
@SuppressWarnings("rawtypes")
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
class DocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    DocumentService service;

    @MockBean
    ApplicationProperties applicationProperties;

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

    @Autowired
    ObjectMapper mapper;

    @BeforeAll
    public static void createValidator() {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @AfterAll
    public static void close() {
        validatorFactory.close();
    }

    @Test
    void createPDF() throws Exception {
        Document doc = Document.builder().userid("john doe").description("invoice").type("pdf").build();
        createDocument(doc, status().isOk());
    }

    @Test
    void createPNG() throws Exception {
        Document doc = Document.builder().userid("john doe").description("invoice").type("png").build();
        createDocument(doc, status().isOk());
    }

    @Test
    void createJPG() throws Exception {
        Document doc = Document.builder().userid("john doe").description("invoice").type("jpg").build();
        createDocument(doc, status().isOk());
    }

    @Test
    void createWithoutDescription() throws Exception {
        Document doc = Document.builder().userid("john doe").type("pdf").build();
        createDocument(doc, status().isOk());
    }

    @Test
    void createWithUnknownType() throws Exception {
        Document document = Document.builder().userid("john").type("doc").description("Bill of Fuel").id(10).build();
        ApiResponse response = ApiResponse.builder().error(Constants.UNSUPPORTED_TYPE).build();
        validationTest(document, response);
    }

    @Test
    void createWithInvalidDescription() throws Exception {
        Document document = Document.builder().userid("john").type("pdf").description("Bill").id(10).build();
        ApiResponse response = ApiResponse.builder().error(Constants.INVALID_DESC).build();
        validationTest(document, response);
    }

    @Test
    void createWithInvalidUserId() throws Exception {
        Document document = Document.builder().type("pdf").description("Bill of Fuel").id(10).build();
        ApiResponse response = ApiResponse.builder().error(Constants.EMPTY_USERID).build();
        validationTest(document, response);
    }

    @Test
    void createWithInvalidType() throws Exception {
        Document document = Document.builder().userid("john").description("Bill of fuel").id(10).build();
        ApiResponse response = ApiResponse.builder().error(Constants.EMPTY_TYPE).build();
        validationTest(document, response);
    }

    @Test
    void update() throws Exception {

        UpdateDocument updateModel = new UpdateDocument("Bill of fuel");
        service.update(Mockito.anyLong(), any(UpdateDocument.class));
        RequestBuilder request = MockMvcRequestBuilders.put("http://localhost:8080/v1/documents/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateModel))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void updateWithWrongDesc() throws Exception {
        UpdateDocument updateModel = new UpdateDocument("Bill");
        updateModel.setDescription("Bill");
        RequestBuilder request = MockMvcRequestBuilders.put("http://localhost:8080/v1/documents/1")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(updateModel))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void get() throws Exception {
        when(service.findByUserIdAndId("john doe", 1))
                .thenReturn(Document.builder().id(1).userid("john doe").description("invoice").type("png").build());
        RequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/v1/documents/john doe/1")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"id\":1,\"userid\":\"john doe\",\"type\":\"png\",\"description\":\"invoice\",\"created\":null}}"))
                .andReturn();
    }

    @Test
    void getUnknown() throws Exception {

        when(service.findByUserIdAndId("john doe", 2))
                .thenReturn(null);
        RequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/v1/documents/john doe/2")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(content().json("{}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getList() throws Exception {

        when(service.findByUserId("john doe"))
                .thenReturn(Arrays.asList(Document.builder().id(1).userid("john doe").description("invoice").type("png").build()));
        RequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/v1/documents/john doe")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[{\"id\":1,\"userid\":\"john doe\",\"type\":\"png\",\"description\":\"invoice\",\"created\":null}]}"))
                .andReturn();
    }

    @Test
    @WithMockUser(authorities = {"ADMIN"})
    void getUnknownUserList() throws Exception {

        when(service.findByUserId("john doe"))
                .thenReturn(Collections.emptyList());
        RequestBuilder request = MockMvcRequestBuilders
                .get("http://localhost:8080/v1/documents/john doe")
                .accept(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":[]}"))
                .andReturn();
    }

    private void createDocument(Document document, ResultMatcher matcher) throws Exception {
        when(service.save(any(Document.class)))
                .thenReturn(document.getId());
        RequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/v1/documents/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(document))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(matcher)
                .andReturn();
    }

    private void validationTest(Document document, ApiResponse response) throws Exception {
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        when(service.save(any(Document.class))).thenThrow(new ConstraintViolationException(violations));
        RequestBuilder request = MockMvcRequestBuilders.post("http://localhost:8080/v1/documents/")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(document))
                .contentType(MediaType.APPLICATION_JSON);
        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(content().json(mapper.writeValueAsString(response)))
                .andReturn();
    }
}