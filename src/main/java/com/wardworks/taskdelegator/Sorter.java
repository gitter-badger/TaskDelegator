/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

import java.util.HashMap;

/**
 *
 * @author Gus
 */
public class Sorter extends TaskHandler{

    HashMap<String, Queue> outputQueues = null;
    Thread thread = null;
    
    public Sorter(Queue inputQueue, HashMap<String, Queue> outputQueues) {
        this.outputQueues = outputQueues;
        this.thread = new Thread(this);
        setInputQueue(inputQueue);
    }
    
    public void start(){
        this.thread.start();
    }
    
    @Override
    public TaskResult processTask(Task task) {
        if(outputQueues.containsKey(task.getType())){
            
            outputQueues.get(task.getType()).addTask(task);
            return new TaskResult(true, null);

        }else{

            System.err.println("No output queue for type: " + task.getType() + ". Add via Delegator.getInstance().addTaskQueue(String, TaskHandler)");
            return new TaskResult(true, null);

        }
    }
    
}
