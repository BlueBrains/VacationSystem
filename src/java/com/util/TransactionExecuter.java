/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

/**
 *
 * @author abd
 * @param <T>
 */
public class TransactionExecuter<T> 
{
    public void execute(RunnableInTransaction<T> tr,T object)
    {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx=session.beginTransaction();
            tr.runInTransaction(session,object);
            tx.commit();
        }
        catch(TransactionException ex)
        {
            if(tx!=null)
                tx.rollback();
        }
    }
}
