/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gus
 */
class Queue {
   
    String type = null;
    PriorityBlockingQueue<Task> queue = null;

    public Queue(String type) {
        
        this.type = type;
        this.queue = new PriorityBlockingQueue<>();
        
    }
    
    public String getType() {
        return type;
    }
    
    public void addTask(Task task){
    
        this.queue.add(task);
    
    }
    
    public Task getTask(){
    
        try {
            Task task = queue.take();
            return task;
        } catch (InterruptedException ex) {
            Logger.getLogger(Queue.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        
    
    }
    
}
