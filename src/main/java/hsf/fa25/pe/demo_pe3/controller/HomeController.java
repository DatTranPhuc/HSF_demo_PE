package hsf.fa25.pe.demo_pe3.controller;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import hsf.fa25.pe.demo_pe3.entity.SonyProducts;
import hsf.fa25.pe.demo_pe3.service.AccountService;
import hsf.fa25.pe.demo_pe3.service.CategoryServices;
import hsf.fa25.pe.demo_pe3.service.ProductService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private AccountService accountsSerivce;
    @Autowired
    private CategoryServices categoriesService;
    @Autowired
    private ProductService productsService;

    @GetMapping("/home")
    public ModelAndView showHomePage(HttpSession session,
                                     @RequestParam(value = "search", required = false) String searchKeyword) {
        SonyAccounts accounts = (SonyAccounts) session.getAttribute("loggerInUser");
        ModelAndView modelAndView = new ModelAndView();

        if (accounts == null) {
            modelAndView.setViewName("redirect:/login");
            return modelAndView;
        }

        if (accounts.getRoleId() != 1 && accounts.getRoleId() != 2) {
            modelAndView.setViewName("redirect:/403");
            return modelAndView;
        }

        List<SonyProducts> sonyProductsList;
        if (searchKeyword != null && !searchKeyword.trim().isEmpty()) {
            sonyProductsList = productsService.searchProductsByName(searchKeyword);
            modelAndView.addObject("searchKeyword", searchKeyword);
        } else {
            sonyProductsList = productsService.getAllSonyProducts();
        }
        modelAndView.addObject("products", sonyProductsList);

        if (accounts.getRoleId() == 1) {
            List<SonyProducts> topProducts = productsService.getTop3ProductsByStockInEachCategory();
            modelAndView.addObject("topProducts", topProducts);
        }

        modelAndView.setViewName("home");
        return modelAndView;
    }

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(HttpSession session, @PathVariable long id) {
        SonyAccounts accounts = (SonyAccounts) session.getAttribute("loggerInUser");
        if (accounts == null || (accounts.getRoleId() != 1)) {
            return "redirect:/403";
        }
        productsService.deleteSonyProducts(id);
        return "redirect:/home";
    }

    @GetMapping("/editProduct/{id}")
    public ModelAndView showEditProductPage(HttpSession session, @PathVariable long id) {
        SonyAccounts accounts = (SonyAccounts) session.getAttribute("loggerInUser");
        ModelAndView modelAndView = new ModelAndView();
        List<SonyCategories> sonyCategories = categoriesService.getAllSonyCategories();
        if (accounts == null || (accounts.getRoleId() != 1)) {
            return new ModelAndView("redirect:/403");
        }
        SonyProducts sonyProducts = productsService.getSonyProductById(id);
        modelAndView.addObject("sonyProducts", sonyProducts);
        modelAndView.addObject("categories", sonyCategories);
        modelAndView.setViewName("editProduct");
        return modelAndView;
    }

    @PostMapping("/editProduct")
    public String editProduct(HttpSession session,
                              @Valid SonyProducts sonyProducts,
                              BindingResult result, @RequestParam("cateId") int cateId,
                              Model model) {
        SonyAccounts accounts = (SonyAccounts) session.getAttribute("loggerInUser");
        if (accounts == null || accounts.getRoleId() != 1) {
            return "redirect:/403";
        }
        if (result.hasErrors()) {
            model.addAttribute("categories", categoriesService.getAllSonyCategories());
            return "editProduct";
        }
        SonyCategories sonyCategories = categoriesService.getSonyCategoryById(cateId);
        sonyProducts.setCategory(sonyCategories);

        productsService.updateSonyProducts(sonyProducts, sonyProducts.getProductId());
        return "redirect:/home";
    }


    @GetMapping("/addNewItem")
    public ModelAndView showAddNewItemPage(HttpSession session) {
        SonyAccounts accounts = (SonyAccounts) session.getAttribute("loggerInUser");
        ModelAndView modelAndView = new ModelAndView();
        List<SonyCategories> sonyCategories = categoriesService.getAllSonyCategories();
        if (accounts == null || (accounts.getRoleId() != 1)) {
            return new ModelAndView("redirect:/403");
        }
        modelAndView.addObject("sonyProducts", new SonyProducts());
        modelAndView.addObject("categories", sonyCategories);
        modelAndView.setViewName("addNewItem");
        return modelAndView;
    }

    @PostMapping("/addNewItem")
    public String addNewItem(@Valid SonyProducts sonyProducts,
                             BindingResult result, @RequestParam("cateId") int cateId, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoriesService.getAllSonyCategories());
            return "addNewItem";
        }
        SonyCategories sonyCategories = categoriesService.getSonyCategoryById(cateId);
        sonyProducts.setCategory(sonyCategories);
        productsService.addSonyProducts(sonyProducts);
        return "redirect:/home";
    }
}