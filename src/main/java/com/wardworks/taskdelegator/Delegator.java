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
public class Delegator implements TaskResultsListener{
    
    private static Delegator instance = null;
    
    public static Delegator getInstance(){
    
        if(instance == null){
            instance = new Delegator();
        }
        
        return instance;
    
    }
    
    Queue inputQueue = null;
    Sorter inputTaskSorter = null;
    HashMap<String, Queue> outputQueues = null;
    HashMap<String, TaskHandler> outputTaskHandlers = null;
    HashMap<String, Thread> taskHandlerThreads = null;

    public Delegator() {
        
        inputQueue = new Queue(null);
        inputQueue.setPriorityBlockingQueue();
        
        inputTaskSorter = new Sorter();
        inputTaskSorter.setQueue(inputQueue);
        
        taskHandlerThreads = new HashMap<>();
        Thread sorterThread = new Thread(inputTaskSorter);
        taskHandlerThreads.put(null, sorterThread);
        sorterThread.start();
        
        outputQueues = new HashMap<>();
        outputTaskHandlers = new HashMap<>();
        
    }
    
    public void pushTask(Task task){
    
        inputQueue.addTask(task);
    
    }
    
    public void addTaskQueue(String type, TaskHandler handler){
        addTaskQueue(type, handler, null);
    }
    
    public void addTaskQueue(String type, TaskHandler handler, TaskResultsListener taskResultsListener){
    
        Queue queue = new Queue(type);
        handler.setQueue(queue);
        new Thread(handler).start();
        outputQueues.put(type, queue);
        outputTaskHandlers.put(type, handler);
        handler.setResultsListener(taskResultsListener == null ? this : taskResultsListener);
        
    }

    @Override
    public void started(Task task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void complete(Object result) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void failed() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class Sorter extends TaskHandler{

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
        
        
    
    
}
