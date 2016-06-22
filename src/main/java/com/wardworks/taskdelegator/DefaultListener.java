/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

/**
 *
 * @author Gus
 */
class DefaultListener implements TaskResultsListener{
    
    @Override
    public void started(Task task) {
        System.out.println("Task with no listener started - " + task.toString());
    }

    @Override
    public void complete(TaskResult result) {
        System.out.println("Task with no listener complete, result - " + result.toString());
    }

    @Override
    public void failed(TaskResult result) {
        System.out.println("Task with no listener complete, result - " + result.toString());
    }
    
}
