/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

import java.io.Serializable;
import javax.persistence.Entity;

/**
 *
 * @author abd
 */
@Entity
public class DivisionManager extends Employee implements Serializable
{

    public DivisionManager() {
    }
    
    public DivisionManager(Employee e) {
        super(e);
    }
    
    public DivisionManager(String fname, String lname) {
        super(fname, lname);
    }
    
}
