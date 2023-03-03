package com.basilisk.validator;

import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueDeliveryNameValidator implements
        ConstraintValidator<UniqueDeliveryName, UpsertDeliveryDTO> {

    @Autowired
    private DeliveryService service;

    @Override
    public void initialize(UniqueDeliveryName constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpsertDeliveryDTO value, ConstraintValidatorContext constraintValidatorContext) {
        Long id = value.getId();
        id = (id == null) ? 0l : id;
        String name = value.getCompanyName();
        Boolean isValid = service.isValidName(name,id);
        return isValid;
    }
}
