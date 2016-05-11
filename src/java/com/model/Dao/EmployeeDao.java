/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.Employee;
import com.util.HibernateUtil;


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
/**
 *
 * @author abd
 */
public class EmployeeDao 
{
    public static void addEmployee(Employee emp)
    {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx=session.beginTransaction();
            session.persist(emp);
            tx.commit();
        }
        catch(TransactionException ex)
        {
            if(tx!=null)
                tx.rollback();
        }
    }
    public static void removeEmployee(Employee emp)
    {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx=session.beginTransaction();
            session.persist(emp);
            tx.commit();
        }
        catch(TransactionException ex)
        {
            if(tx!=null)
                tx.rollback();
        }
    }
}
