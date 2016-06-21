/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.wardworks.taskdelegator.Delegator;
import org.junit.Test;
import com.wardworks.taskdelegator.Task;
import com.wardworks.taskdelegator.TaskHandler;
import com.wardworks.taskdelegator.TaskPriority;
import com.wardworks.taskdelegator.TaskResult;
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
    
        final Object[] results = new Object[5];
        
        Delegator delegator = Delegator.getInstance();
        
        delegator.addTaskQueue("test", new TaskHandler() {

            @Override
            public TaskResult processTask(Task task) {
                results[0] = true;
                results[1] = task.getType();
                return new TaskResult(true, null);
            }
            
        });
        
        delegator.pushTask(new Task("test"));
        
        Thread.sleep(200);
        
        Assert.assertTrue((Boolean)results[0] == true);
        
        Assert.assertTrue(results[1].equals("test"));
        
    }
    
    @Test
    public void testDelegatorPriority() throws InterruptedException{
    
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
                    Thread.sleep(10);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UnitTests.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return new TaskResult(true, null);
            }
        });
        
        /**
         * add tasks to the delegator with even priorities
         */
        
        for(int i = 0; i < 100; i++){
        
            delegator.pushTask(new Task("test", TaskPriority.LOW));
            delegator.pushTask(new Task("test", TaskPriority.NORMAL));
            delegator.pushTask(new Task("test", TaskPriority.HIGH));
        
        }
        
        Thread.sleep(1000);
        
        System.out.println(Arrays.toString(results));
        
        /**
         * more or at least the same amount of high priority tasks handled compared to normal priority
         * more or at least the same amount of normal priority tasks handled compared to low priority
         */
        
        Assert.assertTrue(results[0] >= results[1]);
        Assert.assertTrue(results[1] >= results[2]);
        
    }
    
}
