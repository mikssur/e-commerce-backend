package com.rest_api.fs14backend.productCategory;

import com.rest_api.fs14backend.product.Product;
import com.rest_api.fs14backend.product.ProductDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductCategoryService {
    @Autowired
    private ProductCategoryRepository categoryRepository;

    public List<ProductCategory> getAll() { return categoryRepository.findAll(); }

    public ProductCategory createOne(ProductCategory category) {
        return categoryRepository.save(category);
    }

    public ProductCategory findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    public void deleteById(Long categoryId) {  categoryRepository.deleteById(categoryId); }
}
