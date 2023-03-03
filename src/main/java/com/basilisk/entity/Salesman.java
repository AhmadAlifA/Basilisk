package com.basilisk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

@Table(name = "Salesmen")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Salesman {

    @Id
    @Column(name = "EmployeeNumber")
    private String employeeNumber;

    @Column(name = "FirstName")
    private String firstName;

    @Column(name = "LastName")
    private String lastName;

    @Column(name = "Level")
    private String level;

    @Column(name = "BirthDate")
    private LocalDate birthDate;

    @Column(name = "HiredDate")
    private LocalDate hiredDate;

    @Column(name = "Address")
    private String Address;

    @Column(name = "City")
    private String city;

    @Column(name = "Phone")
    private String phone;

    @Column(name = "SuperiorEmployeeNumber")
    private String superiorEmployeeNumber;

    @ManyToOne
    @JoinColumn(name="SuperiorEmployeeNumber", insertable = false, updatable = false)
    private Salesman superior;

    @ManyToMany
    @JoinTable(
            name="SalesmenRegions",
            joinColumns = @JoinColumn(name = "SalesmanEmployeeNumber"),
            inverseJoinColumns = @JoinColumn(name = "RegionId")
    )
    private List<Region> region;

    public Salesman(String employeeNumber, String firstName, String lastName, String level, LocalDate birthDate, LocalDate hiredDate, String address, @Size(max = 50, message = "Nama kota tidak boleh lebih dari 50 characters.") String city, @Size(max = 50, message = "Nomor Telfon tidak boleh lebih dari 50 characters.") String phone, String superiorEmployeeNumber) {
        this.employeeNumber = employeeNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.birthDate = birthDate;
        this.hiredDate = hiredDate;
        Address = address;
        this.city = city;
        this.phone = phone;
        this.superiorEmployeeNumber = superiorEmployeeNumber;
    }
}
