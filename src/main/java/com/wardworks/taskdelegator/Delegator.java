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
    HashMap<String, Queue> taskQueues = null;
    HashMap<String, TaskHandler> taskHandlers = null;
    HashMap<String, Thread> taskHandlerThreads = null;

    public Delegator() {
        
        inputQueue = new Queue(null);
        taskHandlerThreads = new HashMap<>();
        taskQueues = new HashMap<>();
        taskHandlers = new HashMap<>();
        
        inputTaskSorter = new Sorter(inputQueue, taskQueues);
        inputTaskSorter.setResultsListener(this);
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
        handler.setResultsListener(taskResultsListener == null ? this : taskResultsListener);
        
        Thread taskThread = new Thread(handler);
        taskHandlerThreads.put(type, taskThread);
        taskHandlers.put(type, handler);
        
        taskThread.start();
        
    }

    @Override
    public void started(Task task) {
        System.out.print("Task with no result listener started - " + task.toString());
    }

    @Override
    public void complete(TaskResult result) {
        System.out.print("Task with no listener complete, result - " + result.toString());
    }

    @Override
    public void failed(TaskResult result) {
        System.out.print("Task with no listener complete, result - " + result.toString());
    }
    
//    private class Sorter extends TaskHandler{
//
//        @Override
//        public TaskResult processTask(Task task) {
//            if(outputQueues.containsKey(task.getType())){
//            
//                outputQueues.get(task.getType()).addTask(task);
//                return new TaskResult(true, null);
//                        
//            }else{
//            
//                System.err.println("No output queue for type: " + task.getType() + ". Add via Delegator.getInstance().addTaskQueue(String, TaskHandler)");
//                return new TaskResult(true, null);
//            
//            }
//            
//        }
//    
//    
//    }
        
        
    
    
}
