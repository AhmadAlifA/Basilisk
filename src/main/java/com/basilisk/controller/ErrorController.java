package com.basilisk.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/error")
public class  ErrorController {

    @GetMapping("/server")
    public String serverError(@RequestParam(required = false) String message, Model model){
        model.addAttribute("errorMessage", message);
        return "server-error";
    }
}
