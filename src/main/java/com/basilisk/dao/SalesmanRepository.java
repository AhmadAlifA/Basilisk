package com.basilisk.dao;

import com.basilisk.dto.SalesmanGridDTO;
import com.basilisk.entity.Salesman;
import com.basilisk.utility.Dropdown;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SalesmanRepository extends JpaRepository<Salesman, String> {

    @Query("""
            SELECT new com.basilisk.dto.SalesmanGridDTO(
            sal.employeeNumber,
            CONCAT(sal.firstName, ' ', sal.lastName) ,
            sal.level,
            CONCAT(sup.firstName, ' ', sup.lastName)
            )
            FROM Salesman AS sal
                INNER JOIN sal.region AS reg
                LEFT JOIN sal.superior AS sup
            WHERE reg.id = :regionId
            AND sal.employeeNumber LIKE %:employeeNumber%
				AND CONCAT(sal.firstName, ' ', sal.lastName) LIKE %:fullName%
				AND sal.level LIKE %:employeeLevel%
				AND (:superiorFullName = '' OR CONCAT(sup.firstName, ' ', sup.lastName) LIKE CONCAT('%',:superiorFullName,'%'))
            """)
    public Page<SalesmanGridDTO> findByRegionId(@Param("regionId") Long regionId,
												@Param("employeeNumber") String employeeNumber,
												@Param("fullName") String fullName,
												@Param("employeeLevel") String employeeLevel,
												@Param("superiorFullName") String superiorFullName,
												Pageable pageable);

   @Query("""
		   SELECT new com.basilisk.dto.SalesmanGridDTO(
            sal.employeeNumber,
            CONCAT(sal.firstName, ' ', sal.lastName) ,
            sal.level,
            CONCAT(sup.firstName, ' ', sup.lastName)
            )
            FROM Salesman AS sal
                LEFT JOIN sal.superior AS sup
            WHERE sal.employeeNumber LIKE %:employeeNumber%
				AND CONCAT(sal.firstName, ' ', sal.lastName) LIKE %:fullName%
				AND sal.level LIKE %:employeeLevel%
				AND (CONCAT(sup.firstName, ' ', sup.lastName) LIKE CONCAT('%',:superriorFullName,'%')
				OR :superriorFullName = '')
		   """)
    public Page<SalesmanGridDTO> findByAll(@Param("employeeNumber") String employeeNumber,
                                         @Param("fullName") String fullName,
                                         @Param("employeeLevel") String employeeLevel,
                                         @Param("superriorFullName") String superriorFullName,
                                         Pageable pageable);

	@Query("""
			SELECT COUNT(sal.employeeNumber)
			FROM Salesman AS sal
			WHERE sal.employeeNumber = :employeeNumber""")
	public  Long countSalesmanByEmployeeNumber(@Param("employeeNumber") String employeeNumber);

	@Query("""
			SELECT new com.basilisk.utility.Dropdown(sal.employeeNumber, CONCAT(sal.firstName , ' ', sal.lastName))
			FROM Salesman AS sal
			ORDER By sal.firstName """)
	public List<Dropdown> findAllOrderByFirstName();

	@Query("""
			SELECT COUNT(sal.employeeNumber)
			FROM Salesman AS sal
			WHERE sal.superiorEmployeeNumber = :superiorEmployeeNumber""")
	public Long countBySuperiorEmployeeNumber(@Param("superiorEmployeeNumber") String superiorEmployeeNumber);
}
