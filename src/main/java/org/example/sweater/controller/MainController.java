package org.example.sweater.controller;

import org.example.sweater.domain.Message;
import org.example.sweater.domain.User;
import org.example.sweater.repos.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private MessageRepo messagesRepo;

    @GetMapping("/")
    // главная страница
    public String greeting(Map<String, Object> model){
        return "greeting";
    }

    //  страница main.mustache с блоками ввода сообщения, фильтрации собщения и списком сообщений
    @GetMapping("/main")
    public String main(Map<String, Object> model){
        Iterable<Message> messageIterable = messagesRepo.findAll();

        model.put("messages", messageIterable);
        return "main";
    }
    // добавления сообщений
    @PostMapping("/add")
    public String add(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam String tag, Map<String, Object> model){
        Message message = new Message(text, tag, user);

        messagesRepo.save(message);

        Iterable<Message> messageIterable = messagesRepo.findAll();

        model.put("messages", messageIterable);

        return "redirect:/main";
    }
    //Контроллер фильтрации сообщений
    @PostMapping("filter")
    public String filter(@RequestParam String filter, Map<String, Object> model){
        Iterable<Message> messages;

        if (filter != null && !filter.isEmpty()) {
            messages = messagesRepo.findByTag(filter);
        } else {
            messages = messagesRepo.findAll();
        }

        model.put("messages", messages);
        return "/main";
    }


}
