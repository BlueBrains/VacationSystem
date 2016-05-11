/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;
import com.util.TransactionExecuter;

import com.model.pojo.Employee;
import com.util.ObjectAdder;
import com.util.ObjectRemover;


/**
 *
 * @author abd
 */
public class EmployeeDao 
{
    public static void addEmployee(Employee emp)
    {
        new TransactionExecuter<Employee>().execute(new ObjectAdder<>(), emp);
    }
    public static void removeEmployee(Employee emp)
    {
        new TransactionExecuter<Employee>().execute(new ObjectRemover<>(), emp);
    }
    
}
