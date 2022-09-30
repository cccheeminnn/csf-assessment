package vttp2022.assessment.csf.orderbackend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class OrderController {
    
    @RequestMapping(path = {"/**"})
    public String index() {
        return "/index.html";
    }
}
