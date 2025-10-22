package hsf.fa25.pe.demo_pe3.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sony_categories")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SonyCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cateId")
    private Integer cateId;

    @Column(name = "cateName", nullable = false, length = 100)
    private String cateName;

    @Column(name = "status", nullable = false)
    private String status;

    // 👇 mappedBy PHẢI = tên field ở SonyProducts (ở trên là "category")
    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<SonyProducts> sonyProducts = new ArrayList<>();
}
