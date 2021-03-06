/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;
import com.util.TransactionExecuter;

import com.model.pojo.Employee;
import com.util.ObjectAdder;
import com.util.ObjectGetter;
import com.util.ObjectRemover;
import com.util.ObjectUpdater;
import com.util.ObjectsGetter;
import com.util.PasswordGenerator;
import com.util.VacationRequestsInitializer;
import java.util.List;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;


/**
 *
 * @author abd
 */
public class EmployeeDao 
{
    public static void initializeVacationRequests(Employee emp)
    {
        new TransactionExecuter<Employee,Void>().execute(new VacationRequestsInitializer<>(), emp);                
    }
    
    public static void addEmployee(Employee emp,String plaintextpassword)
    {                
        PasswordGenerator.generatePassword(emp, plaintextpassword);
        new TransactionExecuter<Employee,Void>().execute(new ObjectAdder<Employee>(), emp);        
    }
    
    public static void removeEmployee(Employee emp)
    {
        new TransactionExecuter<Employee,Void>().execute(new ObjectRemover<Employee>(), emp);
    }    
    public static void updateEmployee(Employee emp)
    {
        new TransactionExecuter<Employee,Void>().execute(new ObjectUpdater<Employee>(),emp);
    }
    public static Employee getEmployee(Integer id)
    {
        return new TransactionExecuter<Integer,Employee>().execute(new ObjectGetter<>(Employee.class),id);        
    }
    public static List<Employee> getEmployees(String order)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee "+TransactionExecuter.getOrderClause(order));        
    }
    public static List<Employee> getEmployeesByFirstName(String firstname,String order)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee where firstname="+firstname+" "+TransactionExecuter.getOrderClause(order));                
    }
    public static List<Employee> getEmployeesByLastName(String lastname,String order)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee where firstname="+lastname+" "+TransactionExecuter.getOrderClause(order));                        
    }
    public static List<Employee> getEmployeesByAddress(String address,String order)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee where address LIKE '%"+address+"%' "+TransactionExecuter.getOrderClause(order));
    }      
    public static List<Employee> getEmployeesByDivision(String division,String order)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee e "
                + "INNER JOIN e.division d where d.name = '"+division+"' "+TransactionExecuter.getOrderClause(order));
    }    
    public static Employee getEmployee(String username)
    {
        return new TransactionExecuter<String,List<Employee>>().execute(new ObjectsGetter<Employee>(),"FROM Employee where username='"+username+"'").get(0);                                
    }
}
