/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

/**
 *
 * @author Gus
 */
public class TaskResult {
    
    private boolean success = true;
    
    private Object result = null;
    
    private String errorMessage = null;

    public TaskResult(boolean success, Object result) {
        
        this(success, result, null);
        
    }
    
    public TaskResult(boolean success, Object result, String errorMessage) {
        
        this.success = success;
        this.result = result;
        this.errorMessage = errorMessage;
        
    }

    public boolean isSuccess() {
        return success;
    }

    public Object getResult() {
        return result;
    }
    
    public String getErrorMessage(){
        return errorMessage;
    }
    
}
