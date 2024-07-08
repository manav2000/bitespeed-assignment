package com.bitespeed.demo.entity;

import com.bitespeed.demo.enums.Precedence;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "contact")
@SQLDelete(sql = "UPDATE contact SET is_deleted=1 WHERE id=?")
@Where(clause = "is_deleted=false")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "linked_precedence")
    @Enumerated(EnumType.STRING)
    private Precedence linkedPrecedence;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    @Column(name = "deleted_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date deletedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    @NotFound(action = NotFoundAction.IGNORE)
    @JsonIgnore
    @ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name = "linked_id")
    private Contact primaryContact;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    @OneToMany(mappedBy = "primaryContact", fetch = FetchType.EAGER)
    private Set<Contact> linkedContacts;

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || obj.getClass()!= this.getClass())
            return false;
        Contact oc = (Contact) obj;
        return this.getEmail().equals(oc.getEmail()) && this.getPhoneNumber().equals(oc.getPhoneNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getEmail(), this.getPhoneNumber());
    }
}
