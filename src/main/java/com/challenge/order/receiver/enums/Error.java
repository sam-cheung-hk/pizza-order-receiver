package com.challenge.order.receiver.enums;

public enum Error {

    // 400 Error

    /**
     * Invalid input parameters
     */
    INVALID_PARAMS("E0001_400_0001", "Invalid input parameters."),

    // 500 Error

    /**
     * Uncaught exception
     */
    UNCAUGHT_EXCEPTION("E0001_500_0001", "System error."),

    /**
     * Error when doing DB CRUD
     */
    DB_CRUD_ERROR("E0001_500_0002", "System error.");

    /**
     * Error code
     */
    private final String errorCode;
    /**
     * Default message returned for the error
     */
    private final String defaultMessage;

    Error(String errorCode, String defaultMessage) {
        this.errorCode = errorCode;
        this.defaultMessage = defaultMessage;
    }

    // Getters

    public String getErrorCode() {
        return errorCode;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
