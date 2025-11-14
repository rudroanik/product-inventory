package com.anik.product_inventory.controller;

import com.anik.product_inventory.model.ProductEntity;
import com.anik.product_inventory.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
@Slf4j
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct( @Valid @RequestBody ProductEntity productEntity) {

        log.debug("Received request to create product: {}", productEntity);

        ProductEntity newProduct = productService.createProduct(productEntity);

        log.info("Product created with ID: {} and SKU: {}", newProduct.getId(), newProduct.getSku());

        return ResponseEntity.ok(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {

        log.debug("Received request to get all products");

        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductEntity> getProductById(@PathVariable Long id) {

        log.debug("Received request to get product by ID: {}", id);

        return ResponseEntity.ok(productService.getProductById(id));

    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@Valid @RequestBody ProductEntity productEntity,@PathVariable Long id) {
        log.debug("Received request to update product: {}", productEntity);
        return ResponseEntity.ok(productService.updateProduct(productEntity,id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {
        log.debug("Received request to delete product by ID: {}", id);
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product deleted");
    }

}
