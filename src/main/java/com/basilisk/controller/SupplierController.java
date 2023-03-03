package com.basilisk.controller;

import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String company,
                        @RequestParam(defaultValue =  "") String contact,
                        @RequestParam(defaultValue =  "") String job,
                        Model model){
        var grid = service.getSupplierGrid(page, company, contact, job);
        var totalHalaman = grid.getTotalPages();
        model.addAttribute("dataGrid",grid);
        model.addAttribute("totalPage",totalHalaman);
        model.addAttribute("currentPage",page);
        model.addAttribute("searchCompany", company);
        model.addAttribute("searchContact", contact);

        return "supplier/supplier-index";
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long id, Model model){
        UpsertSupplierDTO dto = new UpsertSupplierDTO();
        if(id!=null){
            dto = service.getUpdate(id);
        }
        model.addAttribute("dto",dto);
        return "supplier/upsert-supplier";
    }
    //     membawa data masuk dari view ke controller (client side ----> servers side)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertSupplierDTO dto,
                       BindingResult bandingResult, Model model) {
        if (bandingResult.hasErrors()){
            return "supplier/upsert-supplier";
        }
        service.saveSupplier(dto);
        return "redirect:/supplier";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/supplier/index";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}
