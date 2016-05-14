/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

import java.io.Serializable;
import java.util.Date;
import org.hibernate.event.spi.PreInsertEvent;
import org.hibernate.event.spi.PreInsertEventListener;

/**
 *
 * @author abd
 */
public class VacationListener implements Serializable,PreInsertEventListener
{
    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        Object entity=event.getEntity();
        if(entity instanceof VacationRequest)    
        {
            Date date=new Date();
            ((VacationRequest)entity).setCreationdate(date);
            String[] properties=event.getPersister().getPropertyNames();
            for(int i=0;i<properties.length;i++)
            {
                if(properties[i].equals("creationdate"))
                {
                    event.getState()[i]=date;
                    break;
                }
            }
        }
        return false;
    }
}
