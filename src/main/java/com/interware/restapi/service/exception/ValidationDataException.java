package com.interware.restapi.service.exception;

import java.util.LinkedHashMap;

/**
 *
 * @author Alfredo Estrada
 */
public class ValidationDataException extends Exception{
    private LinkedHashMap<String,String> validationErrors;
    
    public ValidationDataException(LinkedHashMap<String,String> validationErrors){
        this.validationErrors=validationErrors;
    }
}
