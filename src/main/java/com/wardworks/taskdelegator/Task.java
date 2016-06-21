/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wardworks.taskdelegator;

import java.util.HashMap;
import javafx.beans.binding.StringBinding;

/**
 *
 * @author Gus
 */
public class Task implements Comparable<Task>{
    
    private int id = TaskConstants.ID_NOT_SET;
    private String type = null;
    private TaskData data = null;
    private TaskPriority priority = TaskPriority.NORMAL;
    private int dependancy = TaskConstants.INDEPENDANT;
    
    public Task(String type) {
        this(type, TaskPriority.NORMAL, TaskConstants.INDEPENDANT);
    }
    
    public Task(String type, TaskPriority priority) {
        this(type, priority, TaskConstants.INDEPENDANT);
    }
    
    public Task(String type, TaskPriority priority, int dependancy) {
        this.type = type;
        this.priority = priority;
        this.dependancy = dependancy;
        this.data = new TaskData(IDGenerator.newID());
    }
    
    @Override
    public int compareTo(Task other) {
        
        if(other.getPriority().ordinal() > getPriority().ordinal()){
            return 1;
        }
        return -1;
        
    }
    
    public boolean hasType(){
        return type != null;
    }
    
    public boolean hasID(){
        return id != TaskConstants.ID_NOT_SET;
    }

    public boolean isDependant(){
        return dependancy != TaskConstants.INDEPENDANT;
    }
    
    public boolean hasData(){
        return !data.isEmpty();
    }
    
    public boolean hasDataKey(String key){
        return data.containsKey(key);
    }
    
    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public Object getObjectData(String key) {
        return data.get(key);
    }
    
    public String getStringData(String key) {
        return (String) data.get(key);
    }
    
    public Object getFloatData(String key) {
        return data.get(key);
    }
    
    public Object getDoubleData(String key) {
        return data.get(key);
    }
    
    public Object getIntegerData(String key) {
        return data.get(key);
    }
    
    public Object getLongData(String key) {
        return data.get(key);
    }

    public void addData(String key, Object data) {
        this.data.put(key, data);
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public int getDependancy() {
        return dependancy;
    }

    public void setDependancy(int dependancy) {
        this.dependancy = dependancy;
    }

    @Override
    public String toString() {
        
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("Task:{");
        
        stringBuilder.append("id=");
        stringBuilder.append(getId());
        stringBuilder.append(", ");
        
        stringBuilder.append("type=");
        stringBuilder.append(getType());
        stringBuilder.append(", ");
        
        stringBuilder.append("priority=");
        stringBuilder.append(getPriority().name());
        stringBuilder.append(", ");
        
        if(dependancy != TaskConstants.INDEPENDANT){
        
            stringBuilder.append("dependancy=");
            stringBuilder.append(getDependancy());
            stringBuilder.append(", ");
        
        }
        
        stringBuilder.append("data=");
        stringBuilder.append(data.toString());
        
        stringBuilder.append("}");
        
        
        return stringBuilder.toString();
    }
    
    
}
