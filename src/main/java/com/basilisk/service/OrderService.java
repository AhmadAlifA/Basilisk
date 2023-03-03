package com.basilisk.service;

import com.basilisk.dao.CustomerRepository;
import com.basilisk.dao.DeliveryRepository;
import com.basilisk.dao.OrderRepository;
import com.basilisk.dao.SalesmanRepository;
import com.basilisk.dto.InsertOrderDTO;
import com.basilisk.dto.OrderGridDTO;
import com.basilisk.dto.UpdateOrderDTO;
import com.basilisk.entity.Delivery;
import com.basilisk.entity.Order;
import com.basilisk.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    SalesmanRepository salesmanRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    DeliveryRepository deliveryRepository;

    public Page<OrderGridDTO> getOrderGrid(Integer pageNumber, String invoiceNumber, Long customerId, String employeeNumber,
                                           Long deliveryId, LocalDate orderDate){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("invoiceNumber"));
        var hasilGrid=orderRepository.findByName(invoiceNumber, customerId, employeeNumber, deliveryId, orderDate, pageable);
        return hasilGrid;
    }

    public List<Dropdown> getCustomerDropdown() {
        List<Dropdown> Dropdowns = customerRepository.findAllOrderByCompanyName();
        return Dropdowns;
    }

    public List<Dropdown> getSalesmanDropdown() {
        List<Dropdown> Dropdowns = salesmanRepository.findAllOrderByFirstName();
        return Dropdowns;
    }

    public List<Dropdown> getDeliveryDropdown() {
        List<Dropdown> Dropdowns = deliveryRepository.findAllOrderByCompanyName();
        return Dropdowns;
    }

    public String saveOrder(InsertOrderDTO dto){
        var nullableDelivery = deliveryRepository.findById(dto.getDeliveryId()).get();
        Delivery delivery = nullableDelivery;
        Double deliveryCost = delivery.getCost();
        Order entity = new Order(
                dto.getInvoiceNumber(),
                dto.getCustomerId(),
                dto.getSalesEmployeeNumber(),
                dto.getOrderDate(),
                dto.getShippedDate(),
                dto.getDueDate(),
                dto.getDeliveryId(),
                deliveryCost,
                dto.getDestinationAddress(),
                dto.getDestinationCity(),
                dto.getDestinationPostalCode()
        );
        Order respond = orderRepository.save(entity);
        return respond.getInvoiceNumber();

    }

    public UpdateOrderDTO getUpdate(String invoiceNumber){
        var entity = orderRepository.findById(invoiceNumber).get();
        UpdateOrderDTO dto= new UpdateOrderDTO(
                entity.getInvoiceNumber(),
                entity.getCustomerId(),
                entity.getSalesEmployeeNumber(),
                entity.getOrderDate(),
                entity.getShippedDate(),
                entity.getDueDate(),
                entity.getDeliveryId(),
                entity.getDestinationAddress(),
                entity.getDestinationCity(),
                entity.getDestinationPostalCode()
        );
        return dto;
    }

    public void updateOrder(UpdateOrderDTO dto) {
        Optional<Delivery> nullableDelivery = deliveryRepository.findById(dto.getDeliveryId());
        Delivery delivery = nullableDelivery.get();
        Double deliveryCost = delivery.getCost();
        Order entity = new Order(
                dto.getInvoiceNumber(),
                dto.getCustomerId(),
                dto.getSalesEmployeeNumber(),
                dto.getOrderDate(),
                dto.getShippedDate(),
                dto.getDueDate(),
                dto.getDeliveryId(),
                deliveryCost,
                dto.getDestinationAddress(),
                dto.getDestinationCity(),
                dto.getDestinationPostalCode()
        );
        orderRepository.save(entity);
    }

    public void deleteOrder(String invoiceNumber) {
        orderRepository.deleteById(invoiceNumber);
    }





    public Boolean checkExistingOrder(String invoiceNumber) {
        Long totalExistingOrder = orderRepository.countByInvoiceNumber(invoiceNumber);
        return (totalExistingOrder > 0) ? true : false;
    }
}
