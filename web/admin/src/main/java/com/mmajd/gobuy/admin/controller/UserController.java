package com.mmajd.gobuy.admin.controller;

import com.mmajd.gobuy.admin.service.RoleService;
import com.mmajd.gobuy.admin.service.UserService;
import com.mmajd.gobuy.common.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @GetMapping()
    public String listAllUsers(Model model) {
        List<UserEntity> users = userService.listAll();
        model.addAttribute("users", users);
        return "users";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        UserEntity user  = new UserEntity();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.listAll());
        return "user_form";
    }

    @PostMapping("/save")
    private String saveUser(UserEntity user, RedirectAttributes attributes) {
        userService.save(user);
        attributes.addFlashAttribute("message", "Saved Successfully");
        return "redirect:/users";
    }
}
