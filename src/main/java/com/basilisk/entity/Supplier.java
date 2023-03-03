package com.basilisk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "Suppliers")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "CompanyName")
    private String companyName;

    @Column(name = "ContactPerson")
    private String contactPerson;

    @Column(name = "JobTitle")
    private String jobTitle;

    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    private String city;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "Email")
    private String emial;

    @Column(name = "DeleteDate")
    private LocalDateTime deleteDate;


}
