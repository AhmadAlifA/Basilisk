package com.basilisk.dao;

import com.basilisk.dto.CustomerGridDTO;
import com.basilisk.entity.Customer;
import com.basilisk.utility.Dropdown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("""
            SELECT new com.basilisk.dto.CustomerGridDTO(cus.id, cus.companyName, cus.contactPerson, cus.city)
            FROM Customer AS cus
            WHERE cus.companyName LIKE %:company% AND
             cus.contactPerson LIKE %:contact% AND
             cus.city LIKE %:city% AND
             cus.deleteDate is NULL
             
            """)
    public Page<CustomerGridDTO> findByName(@Param("company") String company,
                                            @Param("contact") String contact,
                                            @Param("city") String city,
                                            Pageable pageable);

    @Query("""
			SELECT new com.basilisk.utility.Dropdown(cus.id, cus.companyName) 
			FROM Customer AS cus
			ORDER By cus.companyName """)
    public List<Dropdown> findAllOrderByCompanyName();
}
