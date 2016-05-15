/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import org.hibernate.Session;
import org.hibernate.SessionException;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;

/**
 *
 * @author abd
 * @param <T>
 * @param <Res>
 */
public class TransactionExecuter<T,Res> 
{
    public Res execute(RunnableInTransaction<T,Res> tr,T object)
    {
        Transaction tx = null;
        Res res;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) 
        {
            tx=session.beginTransaction();
            res=tr.runInTransaction(session,object);
            tx.commit();
        }
        catch(TransactionException|SessionException ex)
        {
            if(tx!=null)
                tx.rollback();
            throw ex;
        }
        return res;
    }
    public static String getOrderClause(String order)
    {
        if((order==null)||("".equals(order)))
            return "";
        return "order by "+order;
    }
}
