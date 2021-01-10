package com.sreyas.skill.challenge.model;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;

class DocumentTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;

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
    public void typeValidation() {
        Document document=Document.builder().description("Invoice").userid("john").type("doc").build();
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void descriptionValidation() {
        Document document=Document.builder().description("inv").userid("john").type("png").build();
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullUserId() {
        Document document=Document.builder().description("invoice").type("png").build();
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void nullType() {
        Document document=Document.builder().description("invoice").userid("john").build();
        Set<ConstraintViolation<Document>> violations = validator.validate(document);
        assertFalse(violations.isEmpty());
    }
}