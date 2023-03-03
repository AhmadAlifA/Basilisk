package com.basilisk.dto;

import com.basilisk.validator.UniqueAssignRegionSalesman;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
@UniqueAssignRegionSalesman(message="This salesman already work in this region.",
        salesmanEmployeeNumberField = "salesmanEmployeeNumber", regionIdField ="regionId")
public class AssignRegionDTO {

    private String salesmanEmployeeNumber;

    @NotNull(message="Region is required")
    private Long regionId;
}
