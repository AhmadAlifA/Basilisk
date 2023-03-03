package com.basilisk.dto;

import com.basilisk.validator.UniqueInvoiceNumber;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class InsertOrderDTO {

    @NotBlank(message="Product name is required.")
    @Size(max=20, message="Invoice number can't be more than 20 characters.")
    @UniqueInvoiceNumber(message="Invoice numer already existed in the database.")
    private String invoiceNumber;

    @NotNull(message="Customer is required.")
    private Long customerId;

    @NotBlank(message="Sales is required.")
    private String salesEmployeeNumber;

    @NotNull(message="Order date is required.")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate orderDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate shippedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;

    @NotNull(message="Delivery company is required.")
    private Long deliveryId;

    @NotBlank(message="Destination address is required.")
    @Size(max=200, message="Destination address can't be more than 200 characters.")
    private String destinationAddress;

    @NotBlank(message="Destination city is required.")
    @Size(max=20, message="Destination city can't be more than 20 characters.")
    private String destinationCity;

    @NotBlank(message="Destination postal code is required.")
    @Size(max=5, message="Destination postal code can't be more than 5 characters.")
    private String destinationPostalCode;
}
