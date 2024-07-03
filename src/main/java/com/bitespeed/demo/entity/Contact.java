package com.bitespeed.demo.entity;

import com.bitespeed.demo.enums.Precedence;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "contact")
@SQLDelete(sql = "UPDATE contact SET is_deleted=1 WHERE id=?")
@Where(clause = "is_deleted=0")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_id")
    private long id;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "linked_id")
    private long linkedId;

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
}
