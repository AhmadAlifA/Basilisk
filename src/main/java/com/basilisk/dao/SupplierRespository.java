package com.basilisk.dao;

import com.basilisk.dto.DropdownDTO;
import com.basilisk.dto.SupplierGridDTO;
import com.basilisk.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierRespository extends JpaRepository<Supplier, Long>{
    @Query("""
            SELECT new com.basilisk.dto.SupplierGridDTO(sup.id, sup.companyName, sup.contactPerson, sup.jobTitle)
            FROM Supplier AS sup
            WHERE sup.companyName LIKE %:company% AND
             sup.contactPerson LIKE %:contact% AND
             sup.jobTitle LIKE %:job% AND
             sup.deleteDate is NULL
             
            """)
    public Page<SupplierGridDTO> findByName(@Param("company") String company,
                                            @Param("contact") String contact,
                                            @Param("job") String job,
                                            Pageable pageable);

    @Query("""
            SELECT new com.basilisk.dto.DropdownDTO(sup.id,sup.companyName)
            FROM Supplier AS sup
            WHERE sup.deleteDate is NULL
            """)
    public List<DropdownDTO> getDropdown();
}

