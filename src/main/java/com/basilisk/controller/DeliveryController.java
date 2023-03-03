package com.basilisk.controller;

import com.basilisk.dto.UpsertDeliveryDTO;
import com.basilisk.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String company,
                        Model model){

            var grid = service.getDeliveryGrid(page, company);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("company", company);

            return "delivery/delivery-index";
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long id, Model model){

            UpsertDeliveryDTO dto = new UpsertDeliveryDTO();
            if(id!=null){
                dto = service.getUpdate(id);
            }
            model.addAttribute("dto",dto);
            return "delivery/upsert-delivery";

    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertDeliveryDTO dto,
                       BindingResult bandingResult, Model model) {

            if (bandingResult.hasErrors()){
                return "delivery/upsert-delivery";
            }
            service.saveDelivery(dto);
            return "redirect:/delivery/index";

    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            service.delete(id);
            return "redirect:/delivery/index";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getMessage());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}
