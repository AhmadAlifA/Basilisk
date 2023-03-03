package com.basilisk.controller;

import com.basilisk.dto.*;
import com.basilisk.service.SalesmanService;
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
@RequestMapping("/salesman")
public class SalesmanController {

    @Autowired
    private SalesmanService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue = "1") Integer page,
                        @RequestParam(defaultValue="") String employeeNumber,
                        @RequestParam(defaultValue="") String fullName,
                        @RequestParam(defaultValue="") String employeeLevel,
                        @RequestParam(defaultValue="") String superiorFullName,
                        Model model){
        var grid = service.getSalesmanGrid(page, employeeNumber, fullName, employeeLevel, superiorFullName);
        var totalHalaman = grid.getTotalPages();
        List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
        model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
        model.addAttribute("dataGrid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("employeeNumber", employeeNumber);
        model.addAttribute("fullName", fullName);
        model.addAttribute("employeeLevel", employeeLevel);
        model.addAttribute("superiorFullName", superiorFullName);
        model.addAttribute("totalPage", totalHalaman);
        model.addAttribute("breadCrumbs", "Salesman Index");
        return "salesman/salesman-index";
    }

    @GetMapping("/detail")
    public String detail(@RequestParam(required = true) String employeeNumber,
                         @RequestParam(defaultValue = "1") Integer page,
                         @RequestParam(defaultValue="") String city,
                         Model model) {
        var header = service.getHeader(employeeNumber);
        var grid = service.getDetailGrid(page, employeeNumber , city);
        var totalHalaman = grid.getTotalPages();
        String breadCrumbs = String.format("Salesman Index / Salesman of %s", header.getFullName());
        model.addAttribute("headerEmployeeNumber", header.getEmployeeNumber());
        model.addAttribute("headerFullName", header.getFullName());
        model.addAttribute("dataGrid", grid);
        model.addAttribute("currentPage", page);
        model.addAttribute("city", city);
        model.addAttribute("totalPage", totalHalaman);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "salesman/display-index";
    }

    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) String employeeNumber, Model model){
//        try {
            UpdateSalesmanDTO dto = new UpdateSalesmanDTO();

            if(employeeNumber!=null){
                dto = service.getUpdate(employeeNumber);
            }
            model.addAttribute("breadCrumbs", "Salesman Index / Insert Salesman");
            List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
            model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
            List<Dropdown> superiorDropdown = service.getSuperiorDropdown();
            model.addAttribute("superiorDropdown", superiorDropdown);
            model.addAttribute("dto",dto);
            return "salesman/upsert-salesman";
//        }catch (Exception exception){
//            return "redirect:/error/server";
//        }
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpdateSalesmanDTO dto,
                       BindingResult bandingResult, Model model) {
//        try {
            if (bandingResult.hasErrors()){
                model.addAttribute("breadCrumbs", "Salesman Index / Update Salesman");
                List<Dropdown> employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
                model.addAttribute("employeeLevelDropdown", employeeLevelDropdown);
                List<Dropdown> superiorDropdown = service.getSuperiorDropdown();
                model.addAttribute("superiorDropdown", superiorDropdown);
                model.addAttribute("dto",dto);
                return "salesman/upsert-salesman";
            }
            service.saveSalesman(dto);
            return "redirect:/salesman/index";
//        }catch (Exception exception){
//            return "redirect:/error/server";
//        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String employeeNumber, Model model) {
        long dependentOrders = service.dependentOrders(employeeNumber);
        long dependentSubordinates = service.dependentSubordinates(employeeNumber);
        if(dependentOrders > 0 || dependentSubordinates > 0) {
            model.addAttribute("dependentOrders", dependentOrders);
            model.addAttribute("dependentSubordinates", dependentSubordinates);
            model.addAttribute("breadCrumbs", "Salesman Index / Fail to Delete Salesman");
            return "salesman/delete-salesman";
        }
        service.delete(employeeNumber);
        return "redirect:/salesman/index";
    }

    @GetMapping("/assignDetailForm")
    public String assignDetailForm(@RequestParam(required = true) String employeeNumber, Model model) {
        AssignRegionDTO dto = new AssignRegionDTO();
        dto.setSalesmanEmployeeNumber(employeeNumber);
        SalesmanHeaderDTO header = service.getSalesmanHeader(employeeNumber);
        String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
        List<Dropdown> regionDropdown = service.getRegionDropdown();
        model.addAttribute("regionSalesman", dto);
        model.addAttribute("regionDropdown", regionDropdown);
        model.addAttribute("breadCrumbs", breadCrumbs);
        return "salesman/salesman-display-form";
    }

    @PostMapping("/assignDetail")
    public String assignDetail(@Valid @ModelAttribute("regionSalesman") AssignRegionDTO dto,
                               BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            SalesmanHeaderDTO header = service.getSalesmanHeader(dto.getSalesmanEmployeeNumber());
            List<Dropdown> regionDropdown = service.getRegionDropdown();
            String breadCrumbs = String.format("Salesman Index / Region of %s / Assign Region", header.getFullName());
            model.addAttribute("regionSalesman", dto);
            model.addAttribute("regionDropdown", regionDropdown);
            model.addAttribute("breadCrumbs", breadCrumbs);
            return "salesman/salesman-display-form";
        } else {
            service.assignRegion(dto);
            redirectAttributes.addAttribute("employeeNumber", dto.getSalesmanEmployeeNumber());
            return "redirect:/salesman/detail";
        }
    }

    @GetMapping("/deleteDetail")
    public String deleteDetail(@RequestParam(required = true) Long regionId,
                               @RequestParam(required = true) String employeeNumber,
                               Model model, RedirectAttributes redirectAttributes) {
        service.detachRegionSalesman(regionId, employeeNumber);
        redirectAttributes.addAttribute("employeeNumber", employeeNumber);
        return "redirect:/salesman/detail";
    }

}
