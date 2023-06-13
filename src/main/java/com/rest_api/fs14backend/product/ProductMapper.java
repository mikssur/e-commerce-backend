package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.productCategory.ProductCategory;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public Product toProduct(ProductDTO product, ProductCategory category){
        return new Product(product.getPrice(), product.getTitle(), product.getDescription(), product.getImage(), product.getQuantity(), category);
    }
}
