package hsf.fa25.pe.demo_pe3.repository;

import hsf.fa25.pe.demo_pe3.entity.SonyProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<SonyProducts, Long> {
    SonyProducts findByProductName(String productName);

    List<SonyProducts> findAllByOrderByProductIdDesc();
    List<SonyProducts> findTop3ByCategory_CateIdOrderByStockDesc(int cateId);

    List<SonyProducts> findByProductNameContainingIgnoreCase(String productName);
}
