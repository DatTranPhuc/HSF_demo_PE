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
        // --- Dữ liệu tài khoản mới ---
        if (accountsSerivce.getAccounts("0888111222", "!Pass1") == null) {
            SonyAccounts accounts = new SonyAccounts();
            accounts.setPhone("0888111222");
            accounts.setPassword("!Pass1");
            accounts.setRoleId(1); // Admin
            accountsSerivce.addAccount(accounts);

            SonyAccounts accounts1 = new SonyAccounts();
            accounts1.setPhone("0888333444");
            accounts1.setPassword("!Pass2");
            accounts1.setRoleId(2);
            accountsSerivce.addAccount(accounts1);

            SonyAccounts accounts2 = new SonyAccounts();
            accounts2.setPhone("0888555666");
            accounts2.setPassword("!Pass3");
            accounts2.setRoleId(3);
            accountsSerivce.addAccount(accounts2);
        }

        if (categoriesService.getCategoryByCateName("Gaming") == null) {
            SonyCategories categories = new SonyCategories();
            categories.setCateName("Gaming");
            categories.setStatus("active");
            categoriesService.addSonyCategories(categories);

            SonyCategories categories1 = new SonyCategories();
            categories1.setCateName("Speakers");
            categories1.setStatus("active");
            categoriesService.addSonyCategories(categories1);

            SonyCategories categories2 = new SonyCategories();
            categories2.setCateName("Storage");
            categories2.setStatus("inactive"); // Thử một trạng thái khác
            categoriesService.addSonyCategories(categories2);
        }

        if (productsService.getSonyProductsByProductName("PlayStation 5 Console") == null) {
            SonyProducts products = new SonyProducts();
            products.setProductName("PlayStation 5 Console");
            products.setPrice(500);
            products.setStock(25);
            products.setCreatedAt(LocalDate.of(2025, 1, 10));
            // Lấy category "Gaming"
            products.setCategory(categoriesService.getCategoryByCateName("Gaming"));
            productsService.addSonyProducts(products);

            SonyProducts products1 = new SonyProducts();
            products1.setProductName("SRS-XV900 X-Series Speaker");
            products1.setPrice(900);
            products1.setStock(12);
            products1.setCreatedAt(LocalDate.of(2025, 2, 15));
            // Lấy category "Speakers"
            products1.setCategory(categoriesService.getCategoryByCateName("Speakers"));
            productsService.addSonyProducts(products1);

            SonyProducts products2 = new SonyProducts();
            products2.setProductName("CFexpress Type B Memory Card");
            products2.setPrice(400);
            products2.setStock(50);
            products2.setCreatedAt(LocalDate.of(2025, 3, 20));
            // Lấy category "Storage"
            products2.setCategory(categoriesService.getCategoryByCateName("Storage"));
            productsService.addSonyProducts(products2);

            SonyProducts products3 = new SonyProducts();
            products3.setProductName("INZONE H9 Wireless Gaming Headset");
            products3.setPrice(300);
            products3.setStock(30);
            products3.setCreatedAt(LocalDate.of(2025, 4, 5));
            // Lấy category "Gaming"
            products3.setCategory(categoriesService.getCategoryByCateName("Gaming"));
            productsService.addSonyProducts(products3);
        }

    }
}
