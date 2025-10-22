package hsf.fa25.pe.demo_pe3.service;



import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import hsf.fa25.pe.demo_pe3.entity.SonyProducts;
import hsf.fa25.pe.demo_pe3.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryServices categoryServices;


    @Override
    public boolean addSonyProducts(SonyProducts sonyProducts) {
        return productRepository.save(sonyProducts) != null;
    }

    @Override
    public SonyProducts getSonyProductsByProductName(String productName) {
        return productRepository.findByProductName(productName);
    }

    @Override
    public List<SonyProducts> getAllSonyProducts() {
        return productRepository.findAllByOrderByProductIdDesc() ;
    }

    @Override
    public void deleteSonyProducts(long id) {
        SonyProducts sonyProducts = productRepository.findById(id).orElse(null);
        if (sonyProducts != null) {
            productRepository.delete(sonyProducts);
        }
    }

    @Override
    public SonyProducts getSonyProductById(long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void updateSonyProducts(SonyProducts sonyProducts, long sonyProductId) {
        SonyProducts existProduct = productRepository.findById(sonyProductId).orElse(null);
        if (existProduct != null) {
            existProduct.setProductName(sonyProducts.getProductName());
            existProduct.setPrice(sonyProducts.getPrice());
            existProduct.setStock(sonyProducts.getStock());
            existProduct.setCategory(sonyProducts.getCategory());
            productRepository.save(existProduct);
        }
    }

    @Override
    public List<SonyProducts> getTop3ProductsByStockInEachCategory() {
        List<SonyProducts> result = new ArrayList<>();
        List<SonyCategories> categories = categoryServices.getAllSonyCategories();

        for (SonyCategories category : categories) {
            List<SonyProducts> top3hHighestStock = productRepository
                    .findTop3ByCategory_CateIdOrderByStockDesc(category.getCateId());
            result.addAll(top3hHighestStock);
        }

        return result;
    }

    @Override
    public List<SonyProducts> searchProductsByName(String productName) {
        if (productName == null || productName.trim().isEmpty()) {
            return productRepository.findAllByOrderByProductIdDesc();
        }
        return productRepository.findByProductNameContainingIgnoreCase(productName.trim());
    }
}
