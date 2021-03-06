package org.example.sweater.controller;

import org.example.sweater.domain.Role;
import org.example.sweater.domain.User;
import org.example.sweater.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

// контроллер формы регистрации
@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;
    // обработка GET запроса
    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }
    // Обработка POST запроса на добавление пользователя
    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
            User userFromDb = userRepo.findByUsername(user.getUsername());
            if ( userFromDb != null){
                model.put("message", "User exists!");
                return "registration";
            }
            user.setActive(true);
            user.setRoles(Collections.singleton(Role.USER));
            userRepo.save(user);
        return "redirect:/login";
    }
}
