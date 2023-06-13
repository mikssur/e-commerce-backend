package com.rest_api.fs14backend.product;

import com.rest_api.fs14backend.exceptions.NotFoundException;

import com.rest_api.fs14backend.productCategory.ProductCategory;
import com.rest_api.fs14backend.productCategory.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("https://e-commerce-frontend-livid.vercel.app/")
@RequestMapping("api/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService categoryService;

    @Autowired
    private ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<Product>> getProduct() {
        try {
            List<Product> products = productService.getAll();
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Product> createOne(@RequestBody ProductDTO productDTO) {
        try {
            Long categoryId = productDTO.getCategoryId();
            ProductCategory category = categoryService.findById(categoryId);

            if (category == null) {
                throw new NotFoundException("Category not found");
            }

            Product product = productService.createProduct(productDTO, category);
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping
    public ResponseEntity<Product> updateOne(@RequestBody ProductDTO productDTO) {
        try {
            Long categoryId = productDTO.getCategoryId();

            ProductCategory category = categoryService.findById(categoryId);
            if (category == null) {
                throw new NotFoundException("Category not found");
            }

            Long productId = productDTO.getId();

            Product product = productService.findById(productId);
            if (product == null) {
                throw new NotFoundException("Product not found");
            }

            Product updatedProduct = productService.updateProduct(product, productDTO, category);

            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                throw new NotFoundException("Product not found");
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public List<Product> searchProduct(@RequestParam String title, @RequestParam Optional<Long> categoryId) {
        if (categoryId.isPresent()) {
            Long categoryIdValue = categoryId.get();
            if (categoryIdValue != null && categoryIdValue != 0) {
                return productService.filterByTitleAndCategory(title, categoryIdValue);
            }
        }

        return productService.filterByTitle(title);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                throw new NotFoundException("Product not found");
            }
            productService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
