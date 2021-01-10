package com.sreyas.skill.challenge.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

import static com.sreyas.skill.challenge.constants.Constants.INVALID_DESC;

/**
 * This class holds the user document details that are updatable
 * Author : Sreyas V Pariyath
 * Date   : 06/12/20
 * Time   : 6:27 PM
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDocument {
    @Size(min = 5, message = INVALID_DESC)
    private String description;
}
