/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import org.hibernate.Session;

/**
 *
 * @author abd
 */
public class ObjectRemover<T> implements RunnableInTransaction<T>
{

    @Override
    public void runInTransaction(Session session, T object) 
    {
        session.delete(object);
    }    
}
