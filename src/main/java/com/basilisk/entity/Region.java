package com.basilisk.entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Table(name = "Regions")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "City")
    private String city;

    @Column(name = "Remark")
    private String remark;

    @ManyToMany
    @JoinTable(
            name="SalesmenRegions",
            joinColumns = @JoinColumn(name = "RegionId"),
            inverseJoinColumns = @JoinColumn(name = "SalesmanEmployeeNumber")
    )
    private List<Salesman> salesmen;

    public Region(Long id, String city, String remark) {
        this.id = id;
        this.city = city;
        this.remark = remark;
    }


}
