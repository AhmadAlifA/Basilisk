package com.basilisk.dao;

import com.basilisk.dto.DeliveryGridDTO;
import com.basilisk.entity.Delivery;
import com.basilisk.utility.Dropdown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    @Query("""
            SELECT new com.basilisk.dto.DeliveryGridDTO(del.id, del.companyName, del.phone, del.cost)
            FROM Delivery AS del
            WHERE del.companyName LIKE %:company% 
             
            """)
    public Page<DeliveryGridDTO> findByName(@Param("company") String company,
                                            Pageable pageable);
    @Query("""
            SELECT COUNT(del.id)
            FROM Delivery AS del
            WHERE del.companyName = :name AND del.id != :id

            """)
    public  Long countByNameAndId(@Param("name") String name,@Param("id") Long id);

    @Query("""
			SELECT new com.basilisk.utility.Dropdown(del.id, del.companyName)
			FROM Delivery AS del
			ORDER By del.companyName """)
    public List<Dropdown> findAllOrderByCompanyName();
}
