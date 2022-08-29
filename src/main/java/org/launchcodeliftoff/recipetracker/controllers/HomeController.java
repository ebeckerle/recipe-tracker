package org.launchcodeliftoff.recipetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    @GetMapping
    public String displayHome(Model model){
        return "index";
    }

    @GetMapping("/{userId}")
    public String displayUserHome(@PathVariable Integer userId){
        return "user-home";
    }
}
