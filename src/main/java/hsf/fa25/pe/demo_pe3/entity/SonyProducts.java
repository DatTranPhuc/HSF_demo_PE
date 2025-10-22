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
    @Column(name = "product_id", nullable = false)
    private int productId;

    @NotBlank
    @Size(min = 5, max = 50)
    @Column(name = "product_name", length = 50, nullable = false)
    private String productName;

    @Min(100)
    @Column(name = "price", nullable = false)
    private Integer price;

    @Min(0) @Max(1000)
    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "create_at", nullable = false)
    private LocalDate createdAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id", nullable = false)
    private SonyCategories category;


    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) this.createdAt = LocalDate.now();
    }
}
