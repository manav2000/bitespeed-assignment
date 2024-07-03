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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/identity")
public class IdentityController {

    @Autowired
    private IdentityManager identityManager;

    @PostMapping
    public ResponseEntity<ContactResponse> createContact(@RequestBody ContactRequest req) {

        List<Contact> contacts = identityManager.createContact(req);

        Optional<Contact> primaryContact = contacts.stream().filter(c -> Precedence.PRIMARY.equals(c.getLinkedPrecedence())).findFirst();

        Long primaryContactId = null;
        List<String> emails = new ArrayList<>();
        List<String> phoneNumbers = new ArrayList<>();
        List<Long> secondaryContactIds = new ArrayList<>();

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
                HttpStatus.CREATED
        );
    }
}
