package com.basilisk.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Table(name = "Products")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Long id;

    @Column(name = "Name")
    private String productName;

    @Column(name = "SupplierId")
    private Long supplierId;

    @ManyToOne
    @JoinColumn(name="SupplierId", insertable = false, updatable = false)
    private Supplier supplier;

    @Column(name = "CategoryId")
    private Long categoryId;

    @ManyToOne
    @JoinColumn(name="CategoryId", insertable = false, updatable = false)
    private Category category;

    @Column(name = "Description")
    private String description;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Stock")
    private Integer stock;

    @Column(name = "OnOrder")
    private Integer onOrder;

    @Column(name = "Discontinue")
    private Boolean discontinue;

    public Product(Long id,
                   String productName,
                   Long supplierId,
                   Long categoryId,
                   String description,
                   Double price,
                   Integer stock,
                   Integer onOrder,
                   Boolean discontinue) {
        this.id = id;
        this.productName = productName;
        this.supplierId = supplierId;
        this.categoryId = categoryId;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.onOrder = onOrder;
        this.discontinue = discontinue;
    }
}
