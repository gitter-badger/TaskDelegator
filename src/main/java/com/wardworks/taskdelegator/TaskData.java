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
public class TaskData {
    
    private int id = TaskConstants.ID_NOT_SET;
    private HashMap<String, Object> data = new HashMap<>();

    public TaskData(int id) {
        
        this.id = id;
        
    }
    
    public int getId() {
        
        return this.id;
        
    }
    
    public void put(String key, Object data){
    
        this.data.put(key, data);
    
    }
    
    public Object get(String key){
    
        return this.data.get(key);
    
    }
    
    public boolean isEmpty(){
    
        return data.isEmpty();
    
    }
    
    public boolean containsKey(String key){
    
        return data.containsKey(key);
    
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        
        stringBuilder.append("TaskData:{");
        
        stringBuilder.append("id=");
        stringBuilder.append(getId());
        stringBuilder.append(", ");
        
        stringBuilder.append("data={");
        
        for(String key : data.keySet()){
        
            stringBuilder.append(key);
            stringBuilder.append("=");
            stringBuilder.append(data.get(key));
            stringBuilder.append(", ");
        
        }
        
        stringBuilder.append("}");
        
        stringBuilder.append("}");
        
        String result = stringBuilder.toString();
        
        result = result.endsWith(",}") ? result.replace(",}", "}") : result;
        
        return result;
    }
    
    
    
}
