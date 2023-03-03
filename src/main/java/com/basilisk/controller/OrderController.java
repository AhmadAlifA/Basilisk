package com.basilisk.controller;

import com.basilisk.dto.InsertOrderDTO;
import com.basilisk.dto.UpdateOrderDTO;
import com.basilisk.service.OrderService;
import com.basilisk.utility.Dropdown;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue="") String invoiceNumber,
                        @RequestParam(required=false) Long customerId,
                        @RequestParam(required=false) String employeeNumber,
                        @RequestParam(required=false) Long deliveryId,
                        @RequestParam(required=false) String orderDate,
                        Model model){
        try {
            LocalDate formattedDate = null;
            if(orderDate != null && orderDate != "") {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                formattedDate = LocalDate.parse(orderDate, formatter);
            }
            var grid = service.getOrderGrid(page, invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate);
            var totalHalaman = grid.getTotalPages();
            List<Dropdown> customerDropdown = service.getCustomerDropdown();
            List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
            List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("deliveryDropdown", deliveryDropdown);
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("breadCrumbs", "Order Index");
            model.addAttribute("invoiceNumber", invoiceNumber);
            model.addAttribute("customerId", customerId);
            model.addAttribute("employeeNumber", employeeNumber);
            model.addAttribute("deliveryId", deliveryId);
            model.addAttribute("orderDate", orderDate);

            return "order/order-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }

    @GetMapping("/upsertForm")
    public String upsertForm(@RequestParam(required = false) String invoiceNumber, Model model) {
        List<Dropdown> customerDropdown = service.getCustomerDropdown();
        List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
        List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
        model.addAttribute("customerDropdown", customerDropdown);
        model.addAttribute("salesmanDropdown", salesmanDropdown);
        model.addAttribute("deliveryDropdown", deliveryDropdown);
        if(invoiceNumber != null) {
            UpdateOrderDTO dto = service.getUpdate(invoiceNumber);
            model.addAttribute("order", dto);
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Order Index / Update Order");
        } else {
            InsertOrderDTO dto = new InsertOrderDTO();
            model.addAttribute("order", dto);
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Order Index / Insert Order");
        }
        return "order/upsert-order";
    }

    @PostMapping("/insert")
    public String insert(@Valid @ModelAttribute("order") InsertOrderDTO dto,
                         BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Dropdown> customerDropdown = service.getCustomerDropdown();
            List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
            List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("deliveryDropdown", deliveryDropdown);
            model.addAttribute("type", "insert");
            model.addAttribute("breadCrumbs", "Order Index / Insert Order");
            return "order/upsert-order";
        } else {
            service.saveOrder(dto);
            return "redirect:/order/index";
        }
    }

    @PostMapping("/update")
    public String update(@Valid @ModelAttribute("order") UpdateOrderDTO dto,
                         BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            List<Dropdown> customerDropdown = service.getCustomerDropdown();
            List<Dropdown> salesmanDropdown = service.getSalesmanDropdown();
            List<Dropdown> deliveryDropdown = service.getDeliveryDropdown();
            model.addAttribute("customerDropdown", customerDropdown);
            model.addAttribute("salesmanDropdown", salesmanDropdown);
            model.addAttribute("deliveryDropdown", deliveryDropdown);
            model.addAttribute("type", "update");
            model.addAttribute("breadCrumbs", "Order Index / Update Order");
            return "order/order-form";
        } else {
            service.updateOrder(dto);
            return "redirect:/order/index";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) String invoiceNumber, Model model) {
        service.deleteOrder(invoiceNumber);
        return "redirect:/order/index";
    }
}
