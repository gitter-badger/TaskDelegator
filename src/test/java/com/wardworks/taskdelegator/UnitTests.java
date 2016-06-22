/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

import org.junit.Test;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
/**
 *
 * @author Gus
 */
public class UnitTests {
    
    @Test
    public void testTask(){
    
        Task task = new Task("test");
        
        Assert.assertTrue(task.getType().equals("test"));
        
        System.out.println(task.toString());
    
    }
    
    @Test
    public void testDelegatorTaskProcessed() throws InterruptedException{
        
        Delegator delegator = Delegator.getInstance();
        
        TestListener testListener = new TestListener();
        
        delegator.addTaskQueue("test", new TaskHandler() {

            @Override
            public TaskResult processTask(Task task) {
                return new TaskResult(true, true);
            }
            
        }, testListener);
        
        delegator.pushTask(new Task("test"));
        
        Thread.sleep(200);
        
        Assert.assertTrue(testListener.getState() == TestListener.State.COMPLETE);
        Assert.assertTrue(testListener.getTask().getType().equals("test"));
        Assert.assertTrue(testListener.getTaskResult().isSuccess());
        Assert.assertTrue((boolean)testListener.getTaskResult().getResult() == true);
        
    }
    
    @Test
    public void testDelegatorPriority() throws InterruptedException{
    
        final int taskTime = 1;
        final int waitTime = taskTime * 150;
        final int inputs = 100;
        
        final int[] results = new int[3];
        
        Delegator delegator = Delegator.getInstance();
        
        delegator.addTaskQueue("test", new TaskHandler() {

            @Override
            public TaskResult processTask(Task task) {
                switch(task.getPriority()){
                
                    case HIGH:
                        results[0] = results[0] + 1;
                        break;
                    case NORMAL:
                        results[1] = results[1] + 1;
                        break;
                    case LOW:
                        results[2] = results[2] + 1;
                        break;
                
                }
                
                try {
                    Thread.sleep(taskTime);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UnitTests.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return new TaskResult(true, null);
            }
        }, new TestListener());
        
        /**
         * add tasks to the delegator with even priorities
         */
        
        for(int i = 0; i < 100; i++){
        
            delegator.pushTask(new Task("test", TaskPriority.LOW));
            delegator.pushTask(new Task("test", TaskPriority.NORMAL));
            delegator.pushTask(new Task("test", TaskPriority.HIGH));
        
        }
        
        Thread.sleep(waitTime);
        
        System.out.println(Arrays.toString(results));
        
        /**
         * All high priority and up to half normal priority tasks should complete
         * within taskTime. Low priority tasks should not start.
         */
        
        Assert.assertTrue(results[0] == inputs);
        Assert.assertTrue(results[1] <= waitTime);
        Assert.assertTrue(results[2] == 0);
        
    }
    
}
