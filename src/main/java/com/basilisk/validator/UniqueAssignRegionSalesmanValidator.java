package com.basilisk.validator;

import com.basilisk.service.RegionService;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueAssignRegionSalesmanValidator implements ConstraintValidator<UniqueAssignRegionSalesman, Object> {

    private String salesmanEmployeeNumberField;
    private String regionIdField;

    public void initialize(UniqueAssignRegionSalesman constraintAnnotation) {
        this.salesmanEmployeeNumberField = constraintAnnotation.salesmanEmployeeNumberField();
        this.regionIdField = constraintAnnotation.regionIdField();
    }

    @Autowired
    private RegionService regionService;

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Long regionIdValue = (Long)(new BeanWrapperImpl(value).getPropertyValue(regionIdField));
        String salesmanEmployeeNumberValue = new BeanWrapperImpl(value).getPropertyValue(salesmanEmployeeNumberField).toString();
        return !regionService.checkExistingRegionSalesman(regionIdValue, salesmanEmployeeNumberValue);
    }
}
