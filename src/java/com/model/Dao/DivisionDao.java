/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.Division;
import com.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

/**
 *
 * @author abd
 */
public class DivisionDao 
{
    public static void addDivision(Division division)
    {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx=session.beginTransaction();
            session.persist(division);
            tx.commit();
        }
        catch(TransactionException ex)
        {
            if(tx!=null)
                tx.rollback();
        }
    }
}
