package com.basilisk.validator;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryNameValidator implements
        ConstraintValidator<UniqueCategoryName, UpsertCategoryDTO> {
    @Autowired
    private CategoryService service;
    //method yang dijalankan pertama kali, sebelum validator memvalidasi
    @Override
    public void initialize(UniqueCategoryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    //method yang menentukan apakah data dalam dto valid atau tidak
    @Override
    public boolean isValid(/*Object*/UpsertCategoryDTO value, ConstraintValidatorContext constraintValidatorContext) {
//        Long id = (Long)(new BeanWrapperImpl(value).getPropertyValue("id"));
        Long id = value.getId();
        id = (id == null) ? 0l : id;
//        String name = (new BeanWrapperImpl(value).getPropertyValue("name")).toString();
        String name = value.getName();
        Boolean isValid = service.isValidName(name,id);
        return isValid;
    }
}
