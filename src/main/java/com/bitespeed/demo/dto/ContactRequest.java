package com.bitespeed.demo.dto;

import com.bitespeed.demo.enums.Precedence;
import com.bitespeed.demo.entity.Contact;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContactRequest {

    private String email;

    private String phoneNumber;

    public Contact toContact() {
        Contact c = new Contact();
        c.setEmail(this.getEmail());
        c.setPhoneNumber(this.getPhoneNumber());
        c.setLinkedPrecedence(Precedence.PRIMARY);
        return c;
    }
}
