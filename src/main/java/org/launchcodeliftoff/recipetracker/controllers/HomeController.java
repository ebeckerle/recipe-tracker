package org.launchcodeliftoff.recipetracker.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String displayHome(){
        return "index";
    }
}
