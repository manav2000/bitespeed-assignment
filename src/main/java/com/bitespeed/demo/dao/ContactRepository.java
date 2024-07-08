package com.bitespeed.demo.dao;

import com.bitespeed.demo.entity.Contact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class ContactRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public Set<Contact> getContactByEmailOrPhnNum(String email, String phoneNumber) {

        return new HashSet<>(entityManager.createQuery("from Contact where email = :email or phoneNumber = :phoneNumber", Contact.class)
                .setParameter("email", email).setParameter("phoneNumber", phoneNumber)
                .getResultList());
    }

    public Contact saveContact(Contact contact) {

        contact.setCreatedAt(new Date());
        contact.setUpdatedAt(new Date());

        entityManager.persist(contact);
        return contact;
    }

    public Contact updateContact(Contact contact) {

        contact.setUpdatedAt(new Date());

        return entityManager.merge(contact);
    }

    public void deleteContact(Contact contact) {

        contact.setDeletedAt(new Date());

        entityManager.remove(contact);
    }
}
