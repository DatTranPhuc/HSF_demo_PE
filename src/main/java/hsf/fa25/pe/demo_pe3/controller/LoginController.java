package hsf.fa25.pe.demo_pe3.controller;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.service.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String showLoginForm() {
        // trả về templates/login.html
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(
            HttpSession session,
            @RequestParam String phone,
            @RequestParam String password,
            RedirectAttributes ra
    ) {
        SonyAccounts acc = accountService.getAccounts(phone, password);

        // Chỉ cho phép role 1 (Admin) và 2 (Staff)
        if (acc == null || (acc.getRoleId() != 1 && acc.getRoleId() != 2)) {
            ra.addFlashAttribute("errorMsg", "You do not have permission to access this function!");
            return "redirect:/login?error";
        }

        // Lưu thông tin user vào session
        session.setAttribute("loggerInUser", acc);

        // Vào trang quản lý
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout";
    }

    @GetMapping("/403")
    public String accessDenied() {
        return "403"; // templates/403.html
    }
}
