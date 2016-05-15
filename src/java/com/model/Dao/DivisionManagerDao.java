/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.DivisionManager;
import com.model.pojo.VacationRequest;
import com.util.ObjectAdder;
import com.util.ObjectRemover;
import com.util.ObjectUpdater;
import com.util.TransactionExecuter;

/**
 *
 * @author abd
 */
public class DivisionManagerDao 
{
    public static void addDivisionManager(DivisionManager mang)
    {
        new TransactionExecuter<DivisionManager,Void>().execute(new ObjectAdder<DivisionManager>(), mang);                
    }
    public static void removeDivisionManager(DivisionManager mang)
    {
        new TransactionExecuter<DivisionManager,Void>().execute(new ObjectRemover<DivisionManager>(), mang);                        
    }
    public static void updateDivisionManager(DivisionManager mang)
    {
        new TransactionExecuter<DivisionManager,Void>().execute(new ObjectUpdater<DivisionManager>(), mang);                                
    }
}
