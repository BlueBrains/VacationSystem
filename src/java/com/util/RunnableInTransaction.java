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
 * @param <T>
 */
public interface RunnableInTransaction<T>
{
    void runInTransaction(Session session,T object);    
}
