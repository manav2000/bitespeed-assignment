package com.bitespeed.demo.manager;

import com.bitespeed.demo.dao.ContactRepository;
import com.bitespeed.demo.enums.Precedence;
import com.bitespeed.demo.dto.ContactRequest;
import com.bitespeed.demo.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class IdentityManager {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public Set<Contact> createContact(ContactRequest request) {

        Contact contact = request.toContact();

        Set<Contact> contacts = contactRepository.getContactByEmailOrPhnNum(contact.getEmail(), contact.getPhoneNumber());

        if(!contacts.isEmpty()) {
            Contact primaryContact = contacts.stream().filter(c -> Precedence.PRIMARY.equals(c.getLinkedPrecedence())).findFirst().orElse(null);

            if(Objects.isNull(primaryContact)) {
                primaryContact = contacts.stream().findFirst().get().getPrimaryContact();
                contacts.add(primaryContact);
            }
            contacts.addAll(primaryContact.getLinkedContacts());
        }

        if(Objects.isNull(contact.getEmail()) || Objects.isNull(contact.getPhoneNumber()) || contacts.contains(contact))
            return contacts;

        contacts.stream().filter(c -> Precedence.PRIMARY.equals(c.getLinkedPrecedence())).findFirst().ifPresentOrElse(c -> {
            contact.setPrimaryContact(c);
            contact.setLinkedPrecedence(Precedence.SECONDARY);
        }, () -> {
            contact.setLinkedPrecedence(Precedence.PRIMARY);
        });

        contacts.add(contactRepository.saveContact(contact));

        return contacts;
    }
}
