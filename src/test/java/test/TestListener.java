/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import com.wardworks.taskdelegator.Task;
import com.wardworks.taskdelegator.TaskResult;
import com.wardworks.taskdelegator.TaskResultsListener;

/**
 *
 * @author Gus
 */
public class TestListener implements TaskResultsListener{

    public enum State {
    
        CREATED,
        STARTED,
        COMPLETE,
        FAILED
    
    }
    
    private State state = State.CREATED;
    private Task task = null;
    private TaskResult taskResult = null;

    public State getState() {
        return state;
    }

    public Task getTask() {
        return task;
    }

    public TaskResult getTaskResult() {
        return taskResult;
    }
    
    @Override
    public void started(Task task) {
        System.out.println("task started - " + task.toString());
        this.state = State.STARTED;
        this.task = task;
    }

    @Override
    public void complete(TaskResult result) {
        System.out.println("task complete.");
        this.state = State.COMPLETE;
        this.taskResult = result;
    }

    @Override
    public void failed(TaskResult result) {
        System.out.println("task failed.");
        this.state = State.FAILED;
        this.taskResult = result;
    }
    
}
