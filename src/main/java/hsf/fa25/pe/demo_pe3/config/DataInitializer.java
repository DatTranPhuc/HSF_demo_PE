package hsf.fa25.pe.demo_pe3.config;

import hsf.fa25.pe.demo_pe3.entity.SonyAccounts;
import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import hsf.fa25.pe.demo_pe3.entity.SonyProducts;
import hsf.fa25.pe.demo_pe3.repository.AccountRepository;
import hsf.fa25.pe.demo_pe3.repository.CategoryRepository;
import hsf.fa25.pe.demo_pe3.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AccountRepository accountRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public DataInitializer(AccountRepository accountRepository,
                           CategoryRepository categoryRepository,
                           ProductRepository productRepository) {
        this.accountRepository = accountRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        ensureDefaultAccount();
        SonyCategories consoles = ensureCategory("PlayStation Consoles");
        SonyCategories accessories = ensureCategory("Accessories");
        SonyCategories audio = ensureCategory("Audio");
        ensureProducts(consoles, accessories, audio);
    }

    private void ensureDefaultAccount() {
        if (accountRepository.count() > 0) {
            return;
        }

        SonyAccounts defaultAccount = new SonyAccounts();
        defaultAccount.setRoleId(1);
        defaultAccount.setPassword("123456");
        defaultAccount.setPhone("0123456789");
        accountRepository.save(defaultAccount);
    }

    private SonyCategories ensureCategory(String name) {
        SonyCategories existing = categoryRepository.findByCateName(name);
        if (existing != null) {
            return existing;
        }

        SonyCategories category = new SonyCategories();
        category.setCateName(name);
        category.setStatus("ACTIVE");
        return categoryRepository.save(category);
    }

    private void ensureProducts(SonyCategories consoles,
                                SonyCategories accessories,
                                SonyCategories audio) {
        if (productRepository.count() > 0) {
            return;
        }

        List<SonyProducts> seedProducts = List.of(
                createProduct("PlayStation 5 Standard", 49900, 15, consoles),
                createProduct("PlayStation 5 Digital Edition", 44900, 12, consoles),
                createProduct("DualSense Wireless Controller", 7999, 40, accessories),
                createProduct("Pulse 3D Wireless Headset", 12999, 20, audio),
                createProduct("PlayStation Portal Remote Player", 19999, 8, accessories)
        );

        productRepository.saveAll(seedProducts);
    }

    private SonyProducts createProduct(String name, int price, int stock, SonyCategories category) {
        SonyProducts product = new SonyProducts();
        product.setProductName(name);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        product.setCreateAt(LocalDate.now());
        return product;
    }
}
