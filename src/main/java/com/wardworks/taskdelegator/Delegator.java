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
public class Delegator{
    
    private static Delegator instance = null;
    
    public static Delegator getInstance(){
    
        if(instance == null){
            instance = new Delegator();
        }
        
        return instance;
    
    }
    
    Queue inputQueue = null;
    Sorter inputTaskSorter = null;
    HashMap<String, Queue> taskQueues = null;
    HashMap<String, TaskHandler> taskHandlers = null;
    HashMap<String, Thread> taskHandlerThreads = null;

    public Delegator() {
        
        inputQueue = new Queue(null);
        taskHandlerThreads = new HashMap<>();
        taskQueues = new HashMap<>();
        taskHandlers = new HashMap<>();
        
        inputTaskSorter = new Sorter(inputQueue, taskQueues);
        inputTaskSorter.setResultsListener(new SorterListener());
        inputTaskSorter.start();
        
    }
    
    public void pushTask(Task task){
    
        inputQueue.addTask(task);
    
    }
    
    public void addTaskQueue(String type, TaskHandler handler){
        addTaskQueue(type, handler, null);
    }
    
    public void addTaskQueue(String type, TaskHandler handler, TaskResultsListener taskResultsListener){
    
        Queue taskQueue = new Queue(type);
        taskQueues.put(type, taskQueue);
        
        handler.setInputQueue(taskQueue);
        handler.setResultsListener(taskResultsListener == null ? new DefaultListener() : taskResultsListener);
        
        Thread taskThread = new Thread(handler);
        taskHandlerThreads.put(type, taskThread);
        taskHandlers.put(type, handler);
        
        taskThread.start();
        
    }
        
        
    
    
}
