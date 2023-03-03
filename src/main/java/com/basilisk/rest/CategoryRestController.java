package com.basilisk.rest;

import com.basilisk.dto.ErrorDTO;
import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.service.CategoryService;
import com.basilisk.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;

@CrossOrigin
@RestController
@RequestMapping("/api/category")
public class CategoryRestController {
    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue =  "1") Integer page,@RequestParam(defaultValue =  "") String name,
                                                       Model model){
        try {
            var grid = service.getCategoryGrid(page, name);
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

    @Operation(summary = "End poin yang digunakan untuk mendapatkan satu category pakai ID-nya(PK)",
        description = "Ini untuk note description End point")
    @GetMapping("/{id}")
    public ResponseEntity<Object> get(@PathVariable(required = true) long id){
        var selected = service.getOneCategory(id);
        return ResponseEntity.status(200).body(selected);
    }

    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        if (!bindingResult.hasErrors()){
            var hasilEntity = service.saveCategory(dto);
            return ResponseEntity.status(201).body(hasilEntity);
        }
        var validationErrors = bindingResult.getAllErrors();
        var formattedErrors = MapperHelper.getErrors(validationErrors);
        return ResponseEntity.status(422).body(formattedErrors);

    }

    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult){
        var hasilEntity = service.saveCategory(dto);
        return ResponseEntity.status(200).body(hasilEntity);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) long id){
        var dependencies = service.delete(id);
        if (dependencies == 0){
        return ResponseEntity.status(204).body(null);
        }
        return ResponseEntity.status(500).body(String.format("Tidak bisa hapus ada %s product yang related", dependencies));
    }
}
