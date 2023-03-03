package com.basilisk.service;

import com.basilisk.dao.CategoryRepository;
import com.basilisk.dao.ProductRepository;
import com.basilisk.dto.CategoryGridDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    //DI Depency Injection
    //IOC Invertion Of Control
@Autowired
    private CategoryRepository categoryRepository;
@Autowired
    private ProductRepository productRepository;

    public Page<CategoryGridDTO> getCategoryGrid(Integer pageNumber, String serachName){
        var pageable = PageRequest.of(pageNumber -1, 10, Sort.by("id"));
    var hasilGrid=categoryRepository.findByName(serachName,pageable);
    return hasilGrid;
    }

    public CategoryGridDTO getOneCategory(long id){
        Category entity=categoryRepository.findById(id).get();
        var dto = new CategoryGridDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return dto;
    }

    public Category saveCategory(UpsertCategoryDTO dto){
        Category entity = new Category(
                dto.getId(),
                dto.getName(),
                dto.getDescription()
        );
        Category categoryBaru = categoryRepository.save(entity);
        return categoryBaru;
    }

    public UpsertCategoryDTO getUpdate(Long id){
        var entity = categoryRepository.findById(id).get();
        UpsertCategoryDTO dto= new UpsertCategoryDTO(
                entity.getId(),
                entity.getName(),
                entity.getDescription()
        );
        return dto;
    }

    public Boolean isValidName(String name, Long id){
        var totalCategory = categoryRepository.countByNameAndId(name,id);
        if (totalCategory >= 1){
            return false;
        }
        return true;
    }

    public Long delete(long id) {
        Long totalProducts = productRepository.countByCategoryId(id);
        if(totalProducts == 0){
        categoryRepository.deleteById(id);
        }
        return totalProducts;
    }

}
