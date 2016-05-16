/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.Division;
import com.util.EmployeesInitializer;
import com.util.ObjectAdder;
import com.util.ObjectGetter;
import com.util.ObjectRemover;
import com.util.TransactionExecuter;
import com.util.ObjectUpdater;
import com.util.ObjectsGetter;
import java.util.List;

/**
 *
 * @author abd
 */
public class DivisionDao 
{
    public static void initializeEmployees(Division division)
    {
        new TransactionExecuter<Division,Void>().execute(new EmployeesInitializer<>(), division);        
    }
    public static void addDivision(Division division)
    {
        new TransactionExecuter<Division,Void>().execute(new ObjectAdder<Division>(), division);
    }
    public static void removeDivision(Division division)
    {
        new TransactionExecuter<Division,Void>().execute(new ObjectRemover<Division>(), division);        
    }
    public static void updateDivision(Division division)
    {
        new TransactionExecuter<Division,Void>().execute(new ObjectUpdater<Division>(), division);        
    }
    public static Division getDivision(Integer id)
    {
        return new TransactionExecuter<Integer,Division>().execute(new ObjectGetter<>(Division.class),id);        
    }
    public static List<Division> getDivisions(String order)
    {
        return new TransactionExecuter<String,List<Division>>().execute(new ObjectsGetter<Division>(),"FROM Division "+TransactionExecuter.getOrderClause(order));                
    }
    public static List<Division> getDivisionsByName(String name,String order)
    {
        return new TransactionExecuter<String,List<Division>>().execute(new ObjectsGetter<Division>(),"FROM Division where name = '"+name+"' "+TransactionExecuter.getOrderClause(order));                        
    }
}
