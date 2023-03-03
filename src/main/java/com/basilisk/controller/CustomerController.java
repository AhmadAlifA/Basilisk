package com.basilisk.controller;

import com.basilisk.dto.UpsertCustomerDTO;
import com.basilisk.dto.UpsertSupplierDTO;
import com.basilisk.service.CustomerService;
import com.basilisk.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String company,
                        @RequestParam(defaultValue =  "") String contact,
                        @RequestParam(defaultValue =  "") String city,
                        Model model){
        var grid = service.getCustomerGrid(page, company, contact, city);
        var totalHalaman = grid.getTotalPages();
        model.addAttribute("dataGrid",grid);
        model.addAttribute("totalPage",totalHalaman);
        model.addAttribute("currentPage",page);
        model.addAttribute("searchCompany", company);
        model.addAttribute("searchContact", contact);
        model.addAttribute("searchCity", city);

        return "customer/customer-index";
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long id, Model model){
        UpsertCustomerDTO dto = new UpsertCustomerDTO();
        if(id!=null){
            dto = service.getUpdate(id);
        }
        model.addAttribute("dto",dto);
        return "customer/upsert-customer";
    }
    //     membawa data masuk dari view ke controller (client side ----> servers side)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertCustomerDTO dto,
                       BindingResult bandingResult, Model model) {
        if (bandingResult.hasErrors()){
            return "customer/upsert-customer";
        }
        service.saveCustomer(dto);
        return "redirect:/customer/index";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/customer/index";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getMessage());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }

    @GetMapping("/info")
    public String insert1(@RequestParam(required = false) Long id, Model model){
        UpsertCustomerDTO dto = new UpsertCustomerDTO();
        if(id!=null){
            dto = service.getUpdate(id);
        }
        model.addAttribute("dto",dto);
        return "customer/info-customer";
    }
}
