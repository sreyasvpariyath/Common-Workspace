package com.sreyas.skill.challenge.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

import static com.sreyas.skill.challenge.constants.Constants.*;

/**
 * This class holds the user document details and mapped to documents table
 * Author : Sreyas V Pariyath
 * Date   : 03/12/20
 * Time   : 7:27 PM
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "documents")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull(message = EMPTY_USERID)
    @Size(min = 1, message = INVALID_USERID)
    private String userid;

    @NotNull(message = EMPTY_TYPE)
    @Pattern(regexp = SUPPORTED_FILE_TYPES, message = UNSUPPORTED_TYPE)
    private String type;

    @Size(min = 5, message = INVALID_DESC)
    private String description;

    @CreationTimestamp
    private Timestamp created;
}
