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
        // ===== Accounts (theo bảng) =====
        ensureAccount("0905111111", "@1", 1);
        ensureAccount("0905222222", "@1", 2);
        ensureAccount("0905333333", "@1", 3);

        // ===== Categories (theo bảng) =====
        SonyCategories headPhone = ensureCategory("HeadPhone", "active");
        SonyCategories cameras   = ensureCategory("Cameras",   "active");
        SonyCategories tvs       = ensureCategory("TVs",       "active");

        if (headPhone == null || cameras == null || tvs == null) {
            return;
        }

        // ===== Products (theo bảng) =====
        ensureProduct(
                "Alpha 1 II - Full-frame Mirrorless",
                6000, 3,
                LocalDate.of(2025, 3, 3),
                cameras
        );

        ensureProduct(
                "Alpha 7C II – Full-frame",
                2000, 5,
                LocalDate.of(2025, 4, 4),
                cameras
        );

        ensureProduct(
                "BRAVIA 8 OLED 4K HDR TV",
                2500, 10,
                LocalDate.of(2025, 1, 1),
                tvs
        );

        ensureProduct(
                "LinkBuds Fit Truly Wireless Noise Canceling",
                180, 15,
                LocalDate.of(2025, 3, 3),
                headPhone
        );
    }

    private void ensureAccount(String phone, String password, int roleId) {
        if (accountsSerivce.getAccounts(phone, password) != null) {
            return; //đã   tồn tại
        }
        SonyAccounts account = new SonyAccounts();
        account.setPhone(phone);
        account.setPassword(password);
        account.setRoleId(roleId);
        accountsSerivce.addAccount(account);
    }

    private SonyCategories ensureCategory(String name, String status) {
        SonyCategories category = categoriesService.getCategoryByCateName(name);
        if (category != null) return category;

        SonyCategories newCategory = new SonyCategories();
        newCategory.setCateName(name);
        newCategory.setStatus(status);

        boolean ok = categoriesService.addSonyCategories(newCategory);
        return ok ? categoriesService.getCategoryByCateName(name) : null;
    }

    private void ensureProduct(String name, int price, int stock, LocalDate createdAt, SonyCategories category) {
        if (productsService.getSonyProductsByProductName(name) != null) {
            return; // đã tồn tại
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
