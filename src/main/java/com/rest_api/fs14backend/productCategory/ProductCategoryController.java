package com.rest_api.fs14backend.productCategory;


import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.order.Order;
import com.rest_api.fs14backend.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin("https://e-commerce-frontend-livid.vercel.app/")
@RequestMapping("api/v1/product-categories")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryRepository repo;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public List<ProductCategory> getProductCategory() {
        return productCategoryService.getAll();
    }

    @PostMapping
    public ResponseEntity<ProductCategory>  createOne(@RequestBody ProductCategory category) {
        try {
            return new ResponseEntity<>(productCategoryService.createOne(category), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id){
        try {
            ProductCategory productCategory = productCategoryService.findById(id);

            if (productCategory == null) {
                throw new NotFoundException("Category not found");
            }

            productCategoryService.deleteById(id);
            return new ResponseEntity<>("Category was deleted", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Internal server error occurred, try to refresh the page", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}