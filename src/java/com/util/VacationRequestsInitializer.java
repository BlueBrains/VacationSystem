/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.pojo.Employee;
import org.hibernate.Hibernate;
import org.hibernate.Session;

/**
 *
 * @author abd
 * @param <In>
 */
public class VacationRequestsInitializer<In extends Employee> implements RunnableInTransaction<In, Void>
{

    @Override
    public Void runInTransaction(Session session, In object) 
    {
        session.refresh(object);
        return null;
    }
    
}
