package com.basilisk.controller;

import com.basilisk.dto.UpsertProductDTO;
import com.basilisk.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/product")
public class ProductController {
        @Autowired
        private ProductService service;

        @GetMapping("/index")
        public String index(@RequestParam(defaultValue =  "1") Integer page,
                            @RequestParam(defaultValue =  "") String name,
                            Model model){
            var grid = service.getProductGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalHalaman);
            model.addAttribute("currentPage",page);
            model.addAttribute("searchProduct", name);

            return "product/product-index";
        }

        @GetMapping("/upsertForm")
        public String insert(@RequestParam(required = false) Long id, Model model){
           try{
               String actionType = "Insert";
               var dto = new UpsertProductDTO();
               dto.setPrice(0.0);
               dto.setStock(0);
               dto.setOnOrder(0);
               var categoryDropdown = service.getCategoryDropdown();
               var supplierDropdown = service.getSupplierDropdown();
               if(id!=null){
                   actionType = "Update";
                   dto = service.getUpdate(id);
               }
               model.addAttribute("actionType",actionType);
               model.addAttribute("dto",dto);
               model.addAttribute("categoryDropdown",categoryDropdown);
               model.addAttribute("supplierDropdown",supplierDropdown);
               return "product/upsert-product";
           }catch (Exception e){
               return "redirect:/error/server";
           }

        }

        @PostMapping("/save")
        public String save(@Valid @ModelAttribute("dto") UpsertProductDTO dto,
                           BindingResult bandingResult, Model model) {
           try{
               if (bandingResult.hasErrors()){
                   var categoryDropdown = service.getCategoryDropdown();
                   var supplierDropdown = service.getSupplierDropdown();
                   model.addAttribute("categoryDropdown",categoryDropdown);
                   model.addAttribute("supplierDropdown",supplierDropdown);
                   model.addAttribute("actionType","Insert");
                   if (dto.getId()!= null){
                       model.addAttribute("actionType","Update");
                   }
                   return "product/upsert-product";
               }
               service.saveProduct(dto);
               return "redirect:/product/index";
           }catch (Exception e){
               return "redirect:/error/server";
           }
        }
}
