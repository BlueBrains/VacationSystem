/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import java.util.List;
import org.hibernate.Session;

/**
 *
 * @author abd
 * @param <Out>
 */
public class ObjectsGetter<Out> implements RunnableInTransaction<String, List<Out>>
{
    @Override
    public List<Out> runInTransaction(Session session, String query) 
    {
        return session.createQuery(query).list();
    }    
}
