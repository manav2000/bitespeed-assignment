package com.bitespeed.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactResponse {

    private long primaryContactId;

    private List<String> emails;

    private List<String> phoneNumbers;

    private List<Long> secondaryContactIds;
}
