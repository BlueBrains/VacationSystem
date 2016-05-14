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
 * @param <Out>
 */
public class ObjectGetter<Out> implements RunnableInTransaction<Integer, Out>
{
    private final Class<Out> theclass;

    public ObjectGetter(Class<Out> theclass) {
        this.theclass = theclass;
    }
    
    @Override
    public Out runInTransaction(Session session, Integer object) 
    {
        return (Out)session.get(theclass, object);
    }
}
