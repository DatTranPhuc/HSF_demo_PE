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

import java.util.Collections;
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
        if (accounts == null) {
            return new ModelAndView("redirect:/login");
        }
        if (accounts.getRoleId() != 1 && accounts.getRoleId() != 2) {
            return new ModelAndView("redirect:/403");
        }

        List<SonyProducts> sonyProductsList =
                (searchKeyword != null && !searchKeyword.trim().isEmpty())
                        ? productsService.searchProductsByName(searchKeyword)
                        : productsService.getAllSonyProducts();

        // luôn add topProducts để tránh null trong view
        List<SonyProducts> topProducts = Collections.emptyList();
        if (accounts.getRoleId() == 1) {
            topProducts = productsService.getTop3ProductsByStockInEachCategory();
        }

        ModelAndView mv = new ModelAndView("home");
        mv.addObject("products", sonyProductsList);
        mv.addObject("topProducts", topProducts);
        mv.addObject("searchKeyword", searchKeyword);
        return mv;
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
        if (accounts == null || (accounts.getRoleId() != 1)) {
            return new ModelAndView("redirect:/403");
        }
        SonyProducts sonyProducts = productsService.getSonyProductById(id);
        List<SonyCategories> sonyCategories = categoriesService.getAllSonyCategories();

        ModelAndView modelAndView = new ModelAndView("editProduct");
        modelAndView.addObject("sonyProducts", sonyProducts);
        modelAndView.addObject("categories", sonyCategories);
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
        if (accounts == null || (accounts.getRoleId() != 1)) {
            return new ModelAndView("redirect:/403");
        }
        List<SonyCategories> sonyCategories = categoriesService.getAllSonyCategories();

        SonyProducts form = new SonyProducts();
        form.setCreatedAt(java.time.LocalDate.now());

        ModelAndView modelAndView = new ModelAndView("addNewItem");
        modelAndView.addObject("sonyProducts", form);
        modelAndView.addObject("categories", sonyCategories);
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
