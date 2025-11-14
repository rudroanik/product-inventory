package com.anik.product_inventory.service;

import com.anik.product_inventory.exception.InvalidSkuFormatException;
import com.anik.product_inventory.exception.ProductNotFoundException;
import com.anik.product_inventory.exception.SkuAlreadyExistsException;
import com.anik.product_inventory.model.ProductEntity;
import com.anik.product_inventory.repository.ProductRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    public final ProductRepository productRepository;

    public ProductEntity createProduct(ProductEntity product) {

        validateSkuFormat(product.getSku());
        checkSkuUnique(product.getSku());

        return productRepository.save(product);
    }

    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    public ProductEntity getProductById(@PathVariable Long id) {
        log.debug("Received request to get product by ID: {}", id);
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException("Product not found with ID: " + id));
    }

    private void validateSkuFormat(String sku) {

        String pattern = "^SKU-[A-Za-z0-9]{8}$";

        if (!sku.matches(pattern)) {
            log.warn("Invalid SKU format: {}", sku);
            throw new InvalidSkuFormatException(
                    "Invalid SKU format. Expected: SKU-XXXXXXXX (8 alphanumeric characters)."
            );
        }
    }

    private void checkSkuUnique(String sku) {
        if (productRepository.existsBySku(sku)) {
            log.warn("SKU already exists: {}", sku);
            throw new SkuAlreadyExistsException("SKU already exists: " + sku);
        }
    }

    public ProductEntity updateProduct(@Valid ProductEntity productEntity, Long id) {

        log.debug("Received request to update product {} with data: {}", id, productEntity);

        ProductEntity existing = productRepository.findById(id)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with id: " + id));

        if (!existing.getSku().equals(productEntity.getSku())) {
            throw new InvalidSkuFormatException("SKU cannot be changed for existing product.");
        }

        validateSkuFormat(productEntity.getSku());

        existing.setName(productEntity.getName());
        existing.setPrice(productEntity.getPrice());
        existing.setQuantity(productEntity.getQuantity());
        existing.setStatus(productEntity.getStatus());
        existing.setDescription(productEntity.getDescription());

        ProductEntity saved = productRepository.save(existing);

        log.info("Product updated with ID: {}", id);

        return saved;

    }

    public void deleteProduct(Long id) {

        log.debug("Received request to delete product ID: {}", id);

        if (!productRepository.existsById(id)) {
            log.warn("Cannot delete. Product not found with ID: {}", id);
            throw new ProductNotFoundException("Product not found with ID: " + id);
        }
    }
}
