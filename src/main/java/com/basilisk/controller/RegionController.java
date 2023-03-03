package com.basilisk.controller;

import com.basilisk.dto.AssignSalesmanDTO;
import com.basilisk.dto.RegionHeaderDTO;
import com.basilisk.dto.UpsertRegionDTO;
import com.basilisk.service.RegionService;
import com.basilisk.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String city,
                        Model model){
        try {
            var grid = service.getRegionGrid(page, city);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchCity", city);

            return "region/region-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) Long id,
                         @RequestParam(defaultValue =  "1") Integer page,
                         @RequestParam(defaultValue="") String employeeNumber,
                         @RequestParam(defaultValue="") String fullName,
                         @RequestParam(defaultValue="") String employeeLevel,
                         @RequestParam(defaultValue="") String superiorFullName,
                        Model model){
//        try {
            var header = service.getHeader(id);
            var grid = service.getDetailGrid(page, id, employeeNumber, fullName, employeeLevel, superiorFullName);
            var totalPage = grid.getTotalPages();
            List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
            model.addAttribute("header",header);
            model.addAttribute("headerId", header.getId());
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalPage);
            model.addAttribute("currentPage",page);
            model.addAttribute("employeeNumber", employeeNumber);
            model.addAttribute("fullName", fullName);
            model.addAttribute("employeeLevel", employeeLevel);
            model.addAttribute("superiorFullName", superiorFullName);
            model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
            model.addAttribute("searchCity",id);

            return "region/display-index";
//        }catch (Exception exception){
//            return "redirect:/error/serverError";
//        }
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long id, Model model){
        try {
            UpsertRegionDTO dto = new UpsertRegionDTO();
            if(id!=null){
                dto = service.getUpdate(id);
            }
            model.addAttribute("dto",dto);
            return "region/upsert-region";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertRegionDTO dto,
                       BindingResult bandingResult, Model model) {
        try {
            if (bandingResult.hasErrors()){
                model.addAttribute("dto",dto);
                return "region/upsert-region";
            }
            service.saveRegion(dto);
            return "redirect:/region/index";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
//        try {
//            Long totalDependecies = service.delete(id);
//            if (totalDependecies == 0){
//                return "redirect:/region/index";
//            }
//            model.addAttribute("dependencies",totalDependecies);
            service.delete(id);
            return "region/delete-region";
//        }catch (Exception exception){
//            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
//            redirectAttributes.addAttribute("message", errorMessage);
//            return "redirect:/error/server";
//        }
    }

    @GetMapping("/assignDetailForm")
    public String assignDetailForm(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes) {
//        try{
        AssignSalesmanDTO dto = new AssignSalesmanDTO();
        dto.setRegionId(id);
        RegionHeaderDTO header = service.getHeader(id);
        String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
        List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
        model.addAttribute("regionSalesman", dto);
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "region/region-display-form";
//        }catch (Exception exception){
//        String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
//        redirectAttributes.addAttribute("message", errorMessage);
//        return "redirect:/error/server";
//        }
    }


    @PostMapping("/assignDetail")
    public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignSalesmanDTO dto,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
//        try {
        if(bindingResult.hasErrors()) {
            RegionHeaderDTO header = service.getHeader(dto.getRegionId());
            List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
            String breadCrumbs = String.format("Region Index / Salesman of %s / Assign Salesman", header.getCity());
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("breadCrumbs", breadCrumbs);
            return "region/region-display-form";
        } else {
            service.assignSalesman(dto);
            redirectAttributes.addAttribute("id", dto.getRegionId());
            return "redirect:/region/detail";
        }
//        }catch (Exception exception){
//            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
//            redirectAttributes.addAttribute("message", errorMessage);
//            return "redirect:/error/server";
//        }
    }

    @GetMapping("/deleteDetail")
    public String deleteDetail(@RequestParam(required = true) Long regionId,
                               @RequestParam(required = true) String employeeNumber,
                               Model model, RedirectAttributes redirectAttributes) {
        service.detachRegionSalesman(regionId, employeeNumber);
        redirectAttributes.addAttribute("id", regionId);
        return "redirect:/region/detail";
    }

}
