package com.basilisk.service;

import com.basilisk.dao.CategoryRepository;
import com.basilisk.dao.ProductRepository;
import com.basilisk.dao.SupplierRespository;
import com.basilisk.dto.*;
import com.basilisk.entity.Product;
import com.basilisk.entity.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service

public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private SupplierRespository supplierRespository;
    public Page<ProductGridDTO> getProductGrid(Integer pageNumber, String name){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
        var hasilGrid=productRepository.findByName(name,pageable);
        return hasilGrid;
    }

    public List<DropdownDTO> getCategoryDropdown(){
        return categoryRepository.getDropdown();
    }

    public List<DropdownDTO> getSupplierDropdown(){
        return supplierRespository.getDropdown();
    }

    public UpsertProductDTO getUpdate(Long id){
        var entity = productRepository.findById(id).get();
        UpsertProductDTO dto= new UpsertProductDTO(
                entity.getId(),
                entity.getProductName(),
                entity.getSupplierId(),
                entity.getCategoryId(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStock(),
                entity.getOnOrder(),
                entity.getDiscontinue()
        );
        return dto;
    }

    @Transactional
    public void saveProduct(UpsertProductDTO dto){
        var entity = new Product(dto.getId(),
                dto.getProductName(),
                dto.getSupplierId(),
                dto.getCategoryId(),
                dto.getDescription(),
                dto.getPrice(),
                dto.getStock(),
                dto.getOnOrder(),
                dto.getDiscontinue());
        productRepository.save(entity);
    }
}
