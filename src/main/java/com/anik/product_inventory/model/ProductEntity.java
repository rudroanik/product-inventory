package com.anik.product_inventory.model;

import com.anik.product_inventory.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Product name is required.")
    private String name;

    @Size(max = 500,message = "Description cannot exceed 500 characters.")
    private String description;

    @NotNull(message = "Price must not be null.")
    @Positive(message = "Price must be a positive number.")
    private Double price;

    @NotBlank(message = "SKU must not be blank.")
    private String sku;

    @NotNull(message = "Quantity must not be null.")
    @Min(value = 0, message = "Quantity cannot be negative.")
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status must not be null.")
    private ProductStatus status;


}
