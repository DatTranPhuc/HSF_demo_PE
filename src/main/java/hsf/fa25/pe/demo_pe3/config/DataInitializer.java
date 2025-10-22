package hsf.fa25.pe.demo_pe3.config;


import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import hsf.fa25.pe.demo_pe3.entity.SonyProducts;
import hsf.fa25.pe.demo_pe3.service.AccountService;
import hsf.fa25.pe.demo_pe3.service.CategoryServices;
import hsf.fa25.pe.demo_pe3.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    AccountService accountsSerivce;
    @Autowired
    ProductService productsService;
    @Autowired
    CategoryServices categoriesService;

    @Override
    public void run(String... args) throws Exception {
        ensureAccount("0888111222", "!Pass1", 1);
        ensureAccount("0888333444", "!Pass2", 2);
        ensureAccount("0888555666", "!Pass3", 3);

        SonyCategories gaming = ensureCategory("Gaming", "active");
        SonyCategories speakers = ensureCategory("Speakers", "active");
        SonyCategories storage = ensureCategory("Storage", "inactive");

        if (gaming == null || speakers == null || storage == null) {
            return;
        }

        ensureProduct("PlayStation 5 Console", 500, 25,
                LocalDate.of(2025, 1, 10), gaming);
        ensureProduct("SRS-XV900 X-Series Speaker", 900, 12,
                LocalDate.of(2025, 2, 15), speakers);
        ensureProduct("CFexpress Type B Memory Card", 400, 50,
                LocalDate.of(2025, 3, 20), storage);
        ensureProduct("INZONE H9 Wireless Gaming Headset", 300, 30,
                LocalDate.of(2025, 4, 5), gaming);
    }

    private void ensureAccount(String phone, String password, int roleId) {
        if (accountsSerivce.getAccounts(phone, password) != null) {
            return;
        }
        SonyAccounts account = new SonyAccounts();
        account.setPhone(phone);
        account.setPassword(password);
        account.setRoleId(roleId);
        accountsSerivce.addAccount(account);
    }

    private SonyCategories ensureCategory(String name, String status) {
        SonyCategories category = categoriesService.getCategoryByCateName(name);
        if (category != null) {
            return category;
        }
        SonyCategories newCategory = new SonyCategories();
        newCategory.setCateName(name);
        newCategory.setStatus(status);
        return categoriesService.addSonyCategories(newCategory) ?
                categoriesService.getCategoryByCateName(name) : null;
    }

    private void ensureProduct(String name, int price, int stock, LocalDate createdAt, SonyCategories category) {
        if (productsService.getSonyProductsByProductName(name) != null) {
            return;
        }
        SonyProducts product = new SonyProducts();
        product.setProductName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCreatedAt(createdAt);
        product.setCategory(category);
        productsService.addSonyProducts(product);
    }
}
