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
        
        inputTaskSorter = new Sorter(outputQueues);
        inputTaskSorter.setInputQueue(inputQueue);
        inputTaskSorter.start();
        
        taskHandlerThreads = new HashMap<>();
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
        handler.setInputQueue(queue);
        Thread t = new Thread(handler);
        taskHandlerThreads.put(type, t);
        t.start();
        outputQueues.put(type, queue);
        outputTaskHandlers.put(type, handler);
        handler.setResultsListener(taskResultsListener == null ? this : taskResultsListener);
        
    }

    @Override
    public void started(Task task) {
        System.out.print("Task with no result listener started - " + task.toString());
    }

    @Override
    public void complete(TaskResult result) {
        System.out.print("Task with no listener complete, result - " + result.getResult().toString());
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
