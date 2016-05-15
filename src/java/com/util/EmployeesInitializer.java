/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.pojo.Division;
import org.hibernate.Session;

/**
 *
 * @author abd
 * @param <In>
 */
public class EmployeesInitializer<In extends Division> implements RunnableInTransaction<In, Void>
{

    @Override
    public Void runInTransaction(Session session, In object) {
        session.refresh(object);
        return null;
    }
    
}
