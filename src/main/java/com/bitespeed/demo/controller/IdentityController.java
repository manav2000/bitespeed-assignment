package com.bitespeed.demo.controller;

import com.bitespeed.demo.enums.Precedence;
import com.bitespeed.demo.dto.ContactRequest;
import com.bitespeed.demo.dto.ContactResponse;
import com.bitespeed.demo.entity.Contact;
import com.bitespeed.demo.manager.IdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    private IdentityManager identityManager;

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@RequestBody ContactRequest req) {

        Set<Contact> contacts = identityManager.createContact(req);

        Optional<Contact> primaryContact = contacts.stream().filter(c -> Precedence.PRIMARY.equals(c.getLinkedPrecedence())).findFirst();

        Long primaryContactId = null;
        Set<String> emails = new LinkedHashSet<>();
        Set<String> phoneNumbers = new LinkedHashSet<>();
        Set<Long> secondaryContactIds = new LinkedHashSet<>();

        if(primaryContact.isPresent()) {
            primaryContactId = primaryContact.get().getId();
            emails.add(primaryContact.get().getEmail());
            phoneNumbers.add(primaryContact.get().getPhoneNumber());
        }

        contacts.forEach(c -> {
            if(Precedence.PRIMARY.equals(c.getLinkedPrecedence()))
                return;
            emails.add(c.getEmail());
            phoneNumbers.add(c.getPhoneNumber());
            secondaryContactIds.add(c.getId());
        });

        return new ResponseEntity<>(
                new ContactResponse(primaryContactId, emails, phoneNumbers, secondaryContactIds),
                HttpStatus.OK
        );
    }
}
