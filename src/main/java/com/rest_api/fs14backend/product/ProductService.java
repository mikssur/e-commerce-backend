package com.rest_api.fs14backend.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.rest_api.fs14backend.exceptions.NotFoundException;
import com.rest_api.fs14backend.productCategory.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAll() { return productRepo.findAll(); }

    public Product createProduct(ProductDTO productDTO, ProductCategory category) {
        // Upload and transform the image
        String transformImageUrl = uploadAndTransformImage(productDTO.getImage());

        ProductDTO newProduct = new ProductDTO();

        newProduct.setTitle(productDTO.getTitle());
        newProduct.setPrice(productDTO.getPrice());
        newProduct.setQuantity(productDTO.getQuantity());
        newProduct.setDescription(productDTO.getDescription());
        newProduct.setImage(transformImageUrl);
        newProduct.setCategoryId(category.getId());

        Product product = productMapper.toProduct(newProduct, category);
        return productRepo.save(product);
    }

    public String uploadAndTransformImage(String imageUrl) {
        try {
            // Upload the image to Cloudinary
            Map<String, Object> uploadResult = cloudinary.uploader().upload(imageUrl, ObjectUtils.emptyMap());

            // Generate a transformed image URL
            String transformedUrl = cloudinary.url()
                    .transformation(new Transformation().width(240).height(240).crop("fill").gravity("center").zoom(0.0))
                    .generate(uploadResult.get("public_id").toString() + "." + uploadResult.get("format").toString());
            return transformedUrl;
        } catch (IOException e) {
            // Handle any exceptions
            return null;
        }
    }

    public  Product updateProduct(Product product, ProductDTO productDTO, ProductCategory category) {
        Double price = productDTO.getPrice();
        String title = productDTO.getTitle();
        String description = productDTO.getDescription();
        String image = productDTO.getImage();
        int quantity = productDTO.getQuantity();

        product.setPrice(price);
        product.setTitle(title);
        product.setDescription(description);
        product.setImage(image);
        product.setQuantity(quantity);
        product.setCategory(category);

        return productRepo.save(product);
    }

    public List<Product> filterByTitle(String title) {
        return productRepo.findAllByTitleContainingIgnoreCase(title);
    }

    public List<Product> filterByTitleAndCategory(String title, Long categoryId) {

        return productRepo.findAllByTitleContainingIgnoreCaseAndCategoryId(title, categoryId);
    }

    public Product findById(Long id) {
        return productRepo.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        productRepo.deleteById(id);
    }
}
