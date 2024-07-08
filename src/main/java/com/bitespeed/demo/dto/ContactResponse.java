package com.bitespeed.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private long primaryContactId;

    private Set<String> emails;

    private Set<String> phoneNumbers;

    private Set<Long> secondaryContactIds;
}
