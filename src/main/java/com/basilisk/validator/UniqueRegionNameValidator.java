package com.basilisk.validator;


import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.dto.UpsertRegionDTO;
import com.basilisk.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueRegionNameValidator implements
        ConstraintValidator<UniqueRegionName, UpsertRegionDTO> {

    @Autowired
    private RegionService service;

    @Override
    public void initialize(UniqueRegionName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpsertRegionDTO value, ConstraintValidatorContext constraintValidatorContext) {
        Long id = value.getId();
        id = (id == null) ? 0l : id;
        String city = value.getCity();
        Boolean isValid = service.isValidCity(city,id);
        return isValid;
    }
}
