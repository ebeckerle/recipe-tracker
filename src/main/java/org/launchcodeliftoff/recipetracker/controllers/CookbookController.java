package org.launchcodeliftoff.recipetracker.controllers;

import org.launchcodeliftoff.recipetracker.data.CookbookRepository;
import org.launchcodeliftoff.recipetracker.data.UserRepository;
import org.launchcodeliftoff.recipetracker.models.Cookbook;
import org.launchcodeliftoff.recipetracker.models.Recipe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/cookbook")
public class CookbookController {

    @Autowired
    private CookbookRepository cookbookRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/add")
    public String displayAddCookbookForm(Model model){
        model.addAttribute(new Cookbook());
        return "add-cookbook";
    }

    @PostMapping("/add")
    public String processAddCookbookForm(@ModelAttribute @Valid Cookbook newCookbook, Errors errors,
//                                   HttpServletRequest request,
                                         Model model){
        if (errors.hasErrors()){
            return "add-recipe";
        }

//        HttpSession session = request.getSession();
//        Integer userId = (Integer) session.getAttribute("user");
//        newCookbook.setUser(userRepository.findById(userId));

        cookbookRepository.save(newCookbook);
        return "view-cookbook";
    }

    @GetMapping("/view/{cookbookId}")
    public String displayViewCookbook(@PathVariable Integer cookbookId, Model model){
        model.addAttribute("cookbook", cookbookRepository.findById(cookbookId));
        return "view-cookbook";
    }
}
