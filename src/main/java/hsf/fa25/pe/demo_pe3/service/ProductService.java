package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyProducts;


import java.util.List;


public interface ProductService {
    public boolean addSonyProducts(SonyProducts sonyProducts);

    public SonyProducts getSonyProductsByProductName(String productName);

    public List<SonyProducts> getAllSonyProducts();

    public void deleteSonyProducts(long id);

    public SonyProducts getSonyProductById(long id);

    public void updateSonyProducts(SonyProducts sonyProducts, long sonyProductId);

    List<SonyProducts> getTop3ProductsByStockInEachCategory();

    List<SonyProducts> searchProductsByName(String productName);

}
