package com.anik.product_inventory.exception;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(String message ) {
        super(message);
    }
}
