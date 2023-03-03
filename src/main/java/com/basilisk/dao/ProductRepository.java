package com.basilisk.dao;

import com.basilisk.dto.ProductGridDTO;
import com.basilisk.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long>{

    @Query("""
            SELECT new com.basilisk.dto.ProductGridDTO(pro.id, pro.productName, cat.name, sup.companyName ,pro.price)
            FROM Product AS pro
            INNER JOIN pro.category AS cat
            LEFT JOIN pro.supplier AS sup
            WHERE pro.productName LIKE %:name%
            """)

    public Page<ProductGridDTO> findByName(@Param("name") String name, Pageable pageable);

    @Query("""
            SELECT COUNT(pro.id)
            FROM Product AS pro
            WHERE pro.categoryId = :categoryId
            """)
    public Long countByCategoryId(@Param("categoryId") Long categoryId);

}
