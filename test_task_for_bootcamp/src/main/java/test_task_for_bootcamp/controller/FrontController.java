
package test_task_for_bootcamp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import test_task_for_bootcamp.model.Category;
import test_task_for_bootcamp.model.Item;
import test_task_for_bootcamp.repositories.CategoryRepository;
import test_task_for_bootcamp.services.CategoryService;
import test_task_for_bootcamp.services.ItemService;
import test_task_for_bootcamp.services.UserService;

import java.util.List;

@Controller

public class FrontController {
    @Autowired
    UserService userService;
    @Autowired
    ItemService itemService;
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = "/")
    public String homePage(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        System.out.println(categories.size());
        if (categories.size()==0) {
            categoryService.addingCategory();
        }
        model.addAttribute("items", itemService.getAllItems());
        model.addAttribute("categories", categories);
        return "home";
    }

    @GetMapping(value = "/{idOfCategory}")
    public String filtredHomePage(Model model,
                                  @PathVariable (name = "idOfCategory") Long id) {
        model.addAttribute("items", itemService.getAllItemsByCategoryId(id));
        model.addAttribute("categories", categoryService.getAllCategories());
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/placeABet")
    public String placeABet(@RequestParam(name = "idOfItem") Long id,
                            @RequestParam(name = "priceOfItem") double price) {
        if (itemService.placeABet(id, price))
            return "redirect:/?succesfully";
        else
            return "redirect:/?error";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/addItem")
    public String addItem(Item item) {
        itemService.addItem(item);
        return "redirect:/?succesfullAdding";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/signin")
    public String signIn(Model model) {
        model.addAttribute("categories", categoryService.getAllCategories());
        return "signinPage";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/profile")
    public String profilePage(Model model) {
        model.addAttribute("user", userService.getUser());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "profilePage";
    }

    @GetMapping(value = "/registratePage")
    public String openRegistratePage(Model model) {

        model.addAttribute("categories", categoryService.getAllCategories());
        return "registratePage";
    }

    @PostMapping(value = "/addUser")
    public String addUser(@RequestParam(name = "user_email") String user_email,
                          @RequestParam(name = "full_name") String full_name,
                          @RequestParam(name = "user_password") String password,
                          @RequestParam(name = "user_rePassword") String rePassword) {
        Boolean bol = userService.addUser(user_email, full_name, password, rePassword);

        if (bol == null) {
            return "redirect:/registratePage?errorEmail";
        } else if (!bol) {
            return "redirect:/registratePage?errorPassword";
        } else {
            return "redirect:/?registrationSuccess";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/changePasswordPage")
    public String openChangePasswordPage() {
        return "changePasswordPage";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/changePassword")
    public String changePassword(@RequestParam(name = "old_password") String oldPassword,
                                 @RequestParam(name = "new_password") String newPassword,
                                 @RequestParam(name = "re_new_password") String reNewPassword) {
        String result = userService.changePassword(oldPassword, newPassword, reNewPassword);
        if (result.equals("passwordChangedSuccesfully")) {
            return "redirect:/profile?" + result;
        } else {
            return "redirect:/changePasswordPage?" + result;
        }
    }
}

