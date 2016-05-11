/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author abd
 */
@Entity
public class Division implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    @Basic(optional=false)
    private String name;
    
    @OneToMany(mappedBy="division",cascade={CascadeType.PERSIST,CascadeType.MERGE})
    private List<Employee> employees;

    @OneToOne(cascade={CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name="division_manager_id",referencedColumnName="id",unique=true)
    private DivisionManager divisionmanager;

    
    
    public DivisionManager getDivisionmanager() {
        return divisionmanager;
    }

    public void setDivisionmanager(DivisionManager divisionmanager) {
        this.divisionmanager = divisionmanager;
    }
    
    
    
    public Division() {
    }

    public Division(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public void addEmployee(Employee emp)
    {
        employees.add(emp);
        emp.setDivision(this);
    }
    public void removeEmployee(Employee emp)
    {
        employees.remove(emp);
        emp.setDivision(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) 
    {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Division)) {
            return false;
        }
        Division other = (Division) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));

    }
    
}
