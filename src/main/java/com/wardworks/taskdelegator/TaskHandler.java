/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gus
 */
public abstract class TaskHandler implements Runnable{

    private boolean enabled = true;
    private Queue inputQueue = null;
    private TaskResultsListener resultsListener = null;
    
    void setInputQueue(Queue queue){
    
        this.inputQueue = queue;
    
    }
    
    void setResultsListener(TaskResultsListener taskResultsListener){
    
        this.resultsListener = taskResultsListener;
    
    }
    
    void cancel(){
    
        enabled = false;
    
    }
    
    @Override
    public final void run(){
        
        while(enabled){
            
            if(inputQueue == null){
            
                System.err.println("Attempeted to start handler with no queue!");
            
            }
        
            Task task = inputQueue.getTask();
            
            this.resultsListener.started(task);
            
            if(task != null){
                
                TaskResult result = processTask(task);
                
                if(result.isSuccess()){
                
                    this.resultsListener.complete(result);
                
                }else{
                
                    this.resultsListener.failed(result);
                
                }
                
            }else{
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Logger.getLogger(TaskHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        }
    };
    
    
    public abstract TaskResult processTask(Task task);
        
    
}
