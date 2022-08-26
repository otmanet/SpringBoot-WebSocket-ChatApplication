package com.example.AppChats.Controller;

import com.example.AppChats.Dto.UserRegisterService;
import com.example.AppChats.Repository.UserRepository;
import com.example.AppChats.Service.UserService;
import com.example.AppChats.model.MessageChat;
import com.example.AppChats.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
public class ControllerChat {
    @Autowired
    private UserRepository userRepository;
     @Autowired
     private JdbcTemplate template;
    @Autowired
    private UserService userService;



    @GetMapping("/")
    public String indexPage(Principal principa, Model model){
        User user=userRepository.getById(principa.getName());
        model.addAttribute("username",user.getUsername());
        return "index";
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public MessageChat sendMessage(@Payload MessageChat chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public MessageChat addUser(@Payload MessageChat chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @GetMapping("/login")
    public String formLogin(){
        return "login";
    }

    @GetMapping("/403")
    public String notfound() {
        return "403";
    }
    @GetMapping("/signup")
    public String Signup(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping("/registerSave")
    public  String regiterUser( @ModelAttribute("user") UserRegisterService registerService){
        userService.save(registerService);
        return "redirect:/login";

    }
}
