package com.basilisk.validator;

import com.basilisk.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueInvoiceNumberValidator implements ConstraintValidator<UniqueInvoiceNumber, String> {

    @Autowired
    private OrderService orderService;

    @Override
    public boolean isValid(String invoiceNumber, ConstraintValidatorContext context) {
        return !orderService.checkExistingOrder(invoiceNumber);
    }
}
