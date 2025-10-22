package hsf.fa25.pe.demo_pe3.service;

import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import hsf.fa25.pe.demo_pe3.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CategoryServicesImpl implements CategoryServices {
    @Autowired
    CategoryRepository sonyCategoriesRepository;

    @Override
    public boolean addSonyCategories(SonyCategories sonyCategories) {
        return sonyCategoriesRepository.save(sonyCategories) != null;
    }

    @Override
    public SonyCategories getCategoryByCateName(String cateName) {
        return sonyCategoriesRepository.findByCateName(cateName);
    }

    @Override
    public List<SonyCategories> getAllSonyCategories() {
        return sonyCategoriesRepository.findAll();
    }

    @Override
    public SonyCategories getSonyCategoryById(int cateId) {
        return sonyCategoriesRepository.findById(cateId).orElse(null);
    }
}
