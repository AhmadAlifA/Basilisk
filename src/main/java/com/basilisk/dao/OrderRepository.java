package com.basilisk.dao;

import com.basilisk.dto.OrderGridDTO;
import com.basilisk.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("""
			SELECT COUNT(ord.id)
			FROM Order AS ord
			WHERE ord.salesEmployeeNumber = :employeeNumber """)
    public Long countByEmployeeNumber(@Param("employeeNumber") String employeeNumber);

	@Query("""
			SELECT new com.basilisk.dto.OrderGridDTO(ord.invoiceNumber, cus.companyName, 
				CONCAT(sal.firstName, ' ', sal.lastName), ord.orderDate, del.companyName)
			FROM Order AS ord
				INNER JOIN ord.customer AS cus
				INNER JOIN ord.salesman AS sal
				INNER JOIN ord.delivery AS del
			WHERE ord.invoiceNumber LIKE %:invoiceNumber%
				AND (:customerId IS NULL OR ord.customerId = :customerId)
				AND (:employeeNumber IS NULL OR ord.salesEmployeeNumber = :employeeNumber)
				AND (:deliveryId IS NULL OR ord.deliveryId = :deliveryId)
				AND (:orderDate IS NULL OR ord.orderDate <= :orderDate) """)
	public Page<OrderGridDTO> findByName(
			@Param("invoiceNumber") String invoiceNumber,
			@Param("customerId") Long customerId,
			@Param("employeeNumber") String employeeNumber,
			@Param("deliveryId") Long deliveryId,
			@Param("orderDate") LocalDate orderDate,
			Pageable pageable);

	@Query("""
			SELECT COUNT(ord.id)
			FROM Order AS ord
			WHERE ord.invoiceNumber = :invoiceNumber """)
	public Long countByInvoiceNumber(@Param("invoiceNumber") String invoiceNumber);
}
