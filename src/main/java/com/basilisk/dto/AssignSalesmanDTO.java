package com.basilisk.dto;

import com.basilisk.validator.UniqueAssignRegionSalesman;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueAssignRegionSalesman(message="This salesman already work in this region.",
        salesmanEmployeeNumberField = "salesmanEmployeeNumber", regionIdField ="regionId")
public class AssignSalesmanDTO {
    private Long regionId;

    @NotBlank(message="Salesman is required")
    private String salesmanEmployeeNumber;

    public AssignSalesmanDTO(String salesmanEmployeeNumber) {
        this.salesmanEmployeeNumber = salesmanEmployeeNumber;
    }

}
