package com.basilisk.service;

import com.basilisk.dao.SupplierRespository;
import com.basilisk.dto.SupplierGridDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class SupplierService {

    @Autowired
    private SupplierRespository supplierRespository;

    public Page<SupplierGridDTO> getSupplierGrid(Integer pageNumber, String company, String contact, String job){
        var pageable1 = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        Page<SupplierGridDTO> hasilGrid1=supplierRespository.findByName(company,contact,job,pageable1);
        return hasilGrid1;
    }

    public SupplierGridDTO getOneSupplier(long id){
        Supplier entity= supplierRespository.findById(id).get();
        var dto = new SupplierGridDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getJobTitle()
        );
        return dto;
    }

    public Supplier saveSupplier(UpsertSupplierDTO dto){
        Supplier entity = new Supplier(
                dto.getId(),
                dto.getCompanyName(),
                dto.getContactPerson(),
                dto.getJobTitle(),
                dto.getAddress(),
                dto.getCity(),
                dto.getPhone(),
                dto.getEmial(),
                dto.getDeleteDate()
        );
        Supplier supplierBaru = supplierRespository.save(entity);
        return supplierBaru;
    }

    public UpsertSupplierDTO getUpdate(Long id){
        var entity = supplierRespository.findById(id).get();
        UpsertSupplierDTO dto;
        dto = new UpsertSupplierDTO(
                entity.getId(),
                entity.getCompanyName(),
                entity.getContactPerson(),
                entity.getJobTitle(),
                entity.getAddress(),
                entity.getCity(),
                entity.getPhone(),
                entity.getEmial(),
                entity.getDeleteDate()
        );
        return dto;
    }

    public void delete(long id){
        var entity = supplierRespository.findById(id).get();
        entity.setDeleteDate(LocalDateTime.now());
        supplierRespository.save(entity);
    }
}
