package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import java.util.List;

public interface CategoryServices {
    boolean addSonyCategories(SonyCategories sonyCategories);
    SonyCategories getCategoryByCateName(String cateName);
    List<SonyCategories> getAllSonyCategories();
    SonyCategories getSonyCategoryById(int cateId);
}
