package com.basilisk.service;

import com.basilisk.dao.CustomerRepository;
import com.basilisk.dto.CustomerGridDTO;
import com.basilisk.dto.UpsertCustomerDTO;
import com.basilisk.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerGridDTO> getCustomerGrid(Integer pageNumber, String company, String contact, String city){
        var pageable1 = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid1=customerRepository.findByName(company,contact,city,pageable1);
        return hasilGrid1;
    }

    public void saveCustomer(UpsertCustomerDTO dto){
        Customer entity = new Customer(dto.getId(),dto.getCompanyName(),dto.getContactPerson(),
                dto.getAddress(),dto.getCity(),dto.getPhone(),dto.getEmial(),dto.getDeleteDate());
        customerRepository.save(entity);
    }

    public UpsertCustomerDTO getUpdate(Long id){
        var entity = customerRepository.findById(id).get();
        UpsertCustomerDTO dto;
        dto = new UpsertCustomerDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPhone(),
                entity.getEmial(),
                entity.getDeleteDate()
        );
        return dto;
    }

    public void delete(long id){
        var entity = customerRepository.findById(id).get();
        entity.setDeleteDate(LocalDateTime.now());
        customerRepository.save(entity);
    }
}
