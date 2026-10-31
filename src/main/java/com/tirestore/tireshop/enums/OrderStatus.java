package com.tirestore.tireshop.enums;

public enum OrderStatus {
    NEW,
    CONFIRMED,
    READY,
    COMPLETED,
    CANCELLED;

   public OrderStatus nextStatus() {
        return switch (this) {
            case NEW -> CONFIRMED;
            case CONFIRMED -> READY;
            case READY -> COMPLETED;
            default -> null;
        };
    }
}
