package com.rest_api.fs14backend.productCategory;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;



@Entity
@Table(name = "productCategories")
@Data
@NoArgsConstructor
public class ProductCategory {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String title;
}
