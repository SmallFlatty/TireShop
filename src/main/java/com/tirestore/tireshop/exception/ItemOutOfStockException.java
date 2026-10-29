package com.tirestore.tireshop.exception;

public class ItemOutOfStockException extends RuntimeException {
    public ItemOutOfStockException(String message) {
        super(message);
    }
}
