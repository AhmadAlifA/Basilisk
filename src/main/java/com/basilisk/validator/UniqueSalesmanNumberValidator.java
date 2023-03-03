package com.basilisk.validator;

import com.basilisk.dto.UpdateSalesmanDTO;
import com.basilisk.service.SalesmanService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSalesmanNumberValidator implements
        ConstraintValidator<UniqueSalesmanNumber, UpdateSalesmanDTO> {

    @Autowired
    private SalesmanService service;

    @Override
    public void initialize(UniqueSalesmanNumber constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UpdateSalesmanDTO value, ConstraintValidatorContext constraintValidatorContext) {
        return !service.isValidNumber(value.getEmployeeNumber());

    }
}
