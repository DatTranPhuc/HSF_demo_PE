package hsf.fa25.pe.demo_pe3.controller;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
public class LoginController {
    @Autowired
    AccountService accountsSerivce;

    @GetMapping
    public String loginPage() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(HttpSession session, @RequestParam("phone") String phone,
                               @RequestParam("password") String password) {
        SonyAccounts sonyAccounts = accountsSerivce.getAccounts(phone, password);
        if (sonyAccounts == null) {
            return "redirect:/login?error";
        }
        session.setAttribute("loggerInUser", sonyAccounts);
        return "redirect:/home";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403";
    }
}
