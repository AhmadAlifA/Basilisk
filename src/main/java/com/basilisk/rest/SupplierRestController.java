package com.basilisk.rest;

import com.basilisk.dto.ErrorDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.entity.Supplier;
import com.basilisk.service.SupplierService;
import com.basilisk.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/supplier")
public class SupplierRestController {

    @Autowired
    private SupplierService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue =  "1") Integer page, @RequestParam(defaultValue =  "") String company,
                                      @RequestParam(defaultValue =  "") String contact, @RequestParam(defaultValue =  "") String job,
                                      Model model){
        try {
            var grid = service.getSupplierGrid(page, company, contact, job);
            return ResponseEntity.status(200/*HttpStatus.OK*/).body(grid);
        }catch (Exception exception){
            var cause = exception.getCause().getCause().toString();
            var errorObject = new ErrorDTO(
                    cause,
                    exception.getMessage(),
                    LocalDateTime.now()
            );
            return ResponseEntity.status(500).body(errorObject);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) long id){
        var selected = service.getOneSupplier(id);
        return ResponseEntity.status(200).body(selected);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
            if(!bindingResult.hasErrors()){
                Supplier hasilEntity = service.saveSupplier(dto);
                return ResponseEntity.status(201).body(hasilEntity);
            }
            var validationErrors = bindingResult.getAllErrors();
            var formattedErrors = MapperHelper.getErrors(validationErrors);
            return ResponseEntity.status(422).body(formattedErrors);
    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult){
        try{
            if(!bindingResult.hasErrors()){
                service.saveSupplier(dto);
                return ResponseEntity.status(HttpStatus.OK).body(dto);
            } else {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Validation Failed, Http Request Body is not validated.");
            }
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        try{
            service.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body(id);
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
        }
    }
}
