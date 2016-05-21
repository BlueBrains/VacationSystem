/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

/**
 *
 * @author abd
 */
import javax.persistence.Entity;

@Entity
public class Administrator extends Employee
{
    public Administrator(Employee e) 
    {
        super(e);
    }

    public Administrator() {
    }

    public Administrator(String fname, String lname) {
        super(fname, lname);
    }
    
}
