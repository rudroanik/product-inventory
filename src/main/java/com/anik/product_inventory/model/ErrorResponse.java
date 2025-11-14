package com.anik.product_inventory.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private int status;
}
