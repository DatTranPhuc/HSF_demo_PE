package hsf.fa25.pe.demo_pe3.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sony_products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SonyProducts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productId", nullable = false)   // DB của bạn dùng camelCase
    private int productId;                          // nếu repo dùng Integer thì đổi cho khớp

    @NotBlank
    @Size(min = 5, max = 50)
    @Column(name = "productName", length = 50, nullable = false)
    private String productName;

    @Min(100)
    @Column(name = "price", nullable = false)
    private Integer price;

    @Min(0) @Max(1000)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "created_at", nullable = false)
    private LocalDate createAt;

    // 👇 Tên field là "category" (quan trọng để khớp mappedBy ở Category)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cateId", nullable = false)
    private SonyCategories category;

    // Nếu bạn muốn bỏ @PrePersist như đã nói trước đây, hãy xóa block này.
    @PrePersist
    protected void onCreate() {
        if (this.createAt == null) this.createAt = LocalDate.now();
    }
}
