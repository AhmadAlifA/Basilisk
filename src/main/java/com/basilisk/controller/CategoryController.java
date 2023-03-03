package com.basilisk.controller;

import com.basilisk.dto.UpsertCategoryDTO;
import com.basilisk.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping("/index")
    public String index(@RequestParam(defaultValue =  "1") Integer page,
                        @RequestParam(defaultValue =  "") String name,
                        Model model){
        try {
            var grid = service.getCategoryGrid(page, name);
            var totalHalaman = grid.getTotalPages();
            model.addAttribute("dataGrid",grid);
            model.addAttribute("totalPage",totalHalaman);
            model.addAttribute("currentPage", page);
            model.addAttribute("searchName", name);

            return "category/category-index";
        }catch (Exception exception){
            return "redirect:/error/serverError";
        }
    }
    // mengeluarkan form ke user
    @GetMapping("/upsertForm")
    public String insert(@RequestParam(required = false) Long id, Model model){
        try {
            UpsertCategoryDTO dto = new UpsertCategoryDTO();
            if(id!=null){
                dto = service.getUpdate(id);
            }
            model.addAttribute("dto",dto);
            return "category/upsert-category";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }
//     membawa data masuk dari view ke controller (client side ----> servers side)
    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("dto") UpsertCategoryDTO dto,
                       BindingResult bandingResult, Model model) {
        try {
            if (bandingResult.hasErrors()){
                return "category/upsert-category";
            }
            service.saveCategory(dto);
            return "redirect:/category/index";
        }catch (Exception exception){
            return "redirect:/error/server";
        }
    }

    @GetMapping("/delete")
    public String delete(@RequestParam(required = true) Long id, Model model, RedirectAttributes redirectAttributes){
        try {
            Long totalDependecies = service.delete(id);
            if (totalDependecies == 0){
                return "redirect:/category/index";
            }
            model.addAttribute("dependencies",totalDependecies);
            return "category/delete-category";
        }catch (Exception exception){
            String errorMessage = String.format("Jenis Exception : %s", exception.getCause().getCause());
            redirectAttributes.addAttribute("message", errorMessage);
            return "redirect:/error/server";
        }
    }
}
