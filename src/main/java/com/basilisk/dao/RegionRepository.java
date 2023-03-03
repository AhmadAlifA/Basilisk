package com.basilisk.dao;

import com.basilisk.dto.RegionGridDTO;
import com.basilisk.entity.Region;
import com.basilisk.utility.Dropdown;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<Region, Long> {
    @Query("""
            SELECT new com.basilisk.dto.RegionGridDTO(reg.id, reg.city, reg.remark)
            FROM Region as reg
            WHERE reg.city LIKE %:cariCity%
            """)
    public Page<RegionGridDTO> findByName(@Param("cariCity") String cariCity, Pageable pageable);

    @Query("""
            SELECT COUNT(reg.id)
            FROM Region AS reg
            WHERE reg.city = :city AND reg.id != :id
            """)
    public  Long countByCityAndId(@Param("city") String name,@Param("id") Long id);

    @Query("""
            SELECT new com.basilisk.dto.RegionGridDTO(reg.id, reg.city, reg.remark)
            FROM Region as reg
                INNER JOIN reg.salesmen AS sal
            WHERE sal.employeeNumber = :employeeNumber AND reg.city LIKE %:city%
            """)
    public Page<RegionGridDTO> findBySalesmanNumber(@Param("employeeNumber") String employeeNumber, @Param("city") String city ,Pageable pageable);

    @Query("""
			SELECT COUNT(reg.id)
			FROM Salesman AS sal
				INNER JOIN sal.region AS reg
			WHERE sal.employeeNumber = :employeeNumber AND reg.id = :regionId
			""")
    public Long count(@Param("employeeNumber") String employeeNumber, @Param("regionId") Long regionId);

    @Query("""
			SELECT new com.basilisk.utility.Dropdown(reg.id, reg.city)
			FROM Region AS reg
			ORDER By reg.city """)
    public List<Dropdown> findAllOrderByCity();
}
