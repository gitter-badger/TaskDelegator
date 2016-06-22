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
public interface TaskResultsListener {
    
    void started(Task task);
    
    void complete(TaskResult result);
    
    void failed(TaskResult result);
    
}
