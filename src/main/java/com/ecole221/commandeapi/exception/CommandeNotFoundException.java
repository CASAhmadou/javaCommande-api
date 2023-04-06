package com.ecole221.commandeapi.exception;

public class CommandeNotFoundException extends CommandeException {
    public CommandeNotFoundException(String message) {
        super(message);
    }

    public CommandeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
