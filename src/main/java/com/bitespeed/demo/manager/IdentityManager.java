package com.bitespeed.demo.manager;

import com.bitespeed.demo.dao.ContactRepository;
import com.bitespeed.demo.enums.Precedence;
import com.bitespeed.demo.dto.ContactRequest;
import com.bitespeed.demo.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IdentityManager {

    @Autowired
    private ContactRepository contactRepository;

    @Transactional
    public List<Contact> createContact(ContactRequest request) {

        Contact contact = request.toContact();

        List<Contact> contacts = contactRepository.getContactByEmailOrPhnNum(contact.getEmail(), contact.getPhoneNumber());

        contacts.stream().filter(c -> Precedence.PRIMARY.equals(c.getLinkedPrecedence())).findFirst().ifPresentOrElse(c -> {
            contact.setLinkedId(c.getId());
            contact.setLinkedPrecedence(Precedence.SECONDARY);
        }, () -> {
            contact.setLinkedPrecedence(Precedence.PRIMARY);
        });

        contacts.add(contactRepository.saveContact(contact));

        return contacts;
    }
}
