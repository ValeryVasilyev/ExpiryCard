package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("title", "Home Page");
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("title", "Home Page");
        return "home";
    }

//    @GetMapping("/blog")
//    public String blogMain(Model model) {
//        Iterable<Post> posts = postRepository.findAll();
//        model.addAttribute("posts", posts);
//        return "blog-main";
//    }
//
//    @Autowired
//    private PostRepository postRepository;

}
