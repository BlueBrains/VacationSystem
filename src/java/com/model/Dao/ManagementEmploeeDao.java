/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.DivisionManager;
import com.model.pojo.ManagementEmployee;
import com.util.ObjectAdder;
import com.util.ObjectRemover;
import com.util.ObjectUpdater;
import com.util.PasswordGenerator;
import com.util.TransactionExecuter;

/**
 *
 * @author abd
 */
public class ManagementEmploeeDao 
{
    public static void addManagementEmployee(ManagementEmployee mang,String plaintextpassword)
    {
        PasswordGenerator.generatePassword(mang, plaintextpassword);
        new TransactionExecuter<ManagementEmployee,Void>().execute(new ObjectAdder<ManagementEmployee>(), mang);                
    }
    public static void removeManagementEmployee(ManagementEmployee mang)
    {
        new TransactionExecuter<ManagementEmployee,Void>().execute(new ObjectRemover<ManagementEmployee>(), mang);                        
    }
    public static void updateManagementEmployee(ManagementEmployee mang)
    {
        new TransactionExecuter<ManagementEmployee,Void>().execute(new ObjectUpdater<ManagementEmployee>(), mang);                                
    }
}
