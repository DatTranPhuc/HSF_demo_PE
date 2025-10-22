// CategoryRepository.java
package hsf.fa25.pe.demo_pe3.repository;
import hsf.fa25.pe.demo_pe3.entity.SonyCategories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<SonyCategories, Integer> {
    public SonyCategories findByCateName(String cateName);
}

