package com.quest.etna.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class Address {


    @Id
    @GeneratedValue
    int id;

    String street;

    @Column(name = "postal_code")
    String postalCode;
    String city;
    String country;

    @ManyToOne
    User user;

    @Column(name = "creation_date")
    Date creationDate;

    @Column(name = "update_date")
    Date updateDate;
}
