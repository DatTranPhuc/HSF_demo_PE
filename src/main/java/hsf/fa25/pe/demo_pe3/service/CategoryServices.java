package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public interface CategoryServices   {

    public boolean addSonyCategories(SonyCategories sonyCategories);

    public SonyCategories getCategoryByCateName(String cateName);

    public List<SonyCategories> getAllSonyCategories();

    public SonyCategories getSonyCategoryById(int cateId);
}
