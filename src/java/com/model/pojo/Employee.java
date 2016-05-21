/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author abd
 */
@Entity
@Inheritance
public class Employee implements Serializable 
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    private Integer id;

    @Basic(optional=false)
    @Column(unique=true)
    private String username;
    
    @Basic(optional=false)
    private String passwordhash;
       
    @Basic(optional=false)
    private String passwordsalt;
    
    @Basic(optional=false)
    private String firstname;

    @Basic(optional=false)
    private String lastname;
    
    private String address;
    
    @Basic(optional=false)
    private Integer salary;
    
    private String phonenumber;
    @OneToMany(mappedBy="employee",cascade={CascadeType.DETACH,CascadeType.REMOVE,CascadeType.REFRESH},fetch=FetchType.LAZY)
    private List<VacationRequest> vacationrequests=new ArrayList<>();

    @ManyToOne(cascade={CascadeType.REFRESH})
    private Division division;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinColumn(name="permission_id")
    private List<EmployeePermission> permissions;

    public List<EmployeePermission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<EmployeePermission> permissions) {
        this.permissions = permissions;
    }  

    public void addPermission(EmployeePermission permission)
    {
        permissions.add(permission);
    }
    
    public void removePermission(EmployeePermission permission)
    {
        permissions.remove(permission);
    }
            
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordhash() {
        return passwordhash;
    }

    public void setPasswordhash(String passwordhash) {
        this.passwordhash = passwordhash;
    }

    public String getPasswordsalt() {
        return passwordsalt;
    }

    public void setPasswordsalt(String passwordsalt) {
        this.passwordsalt = passwordsalt;
    }
    
    
    public Employee(Employee e){
        setAddress(e.getAddress());
        setDivision(e.getDivision());
        setFirstname(e.getFirstname());
        setLastname(e.getLastname());
        setId(e.getId());
        setPhonenumber(e.getPhonenumber());
        setSalary(e.getSalary());      
        setUsername(e.getUsername());
        setPasswordhash(e.getPasswordhash());
        setPasswordsalt(e.getPasswordsalt());
    }
    public Division getDivision() {
        return division;
    }

    public void setDivision(Division division) {
        this.division = division;
    }
    
    public String getFullName()
    {
        if(firstname!=null&&lastname!=null)            
            return firstname+' '+lastname;
        return "";
    }

    public List<VacationRequest> getVacationrequests() {
        return vacationrequests;
    }

    public void setVacationrequests(List<VacationRequest> vacationrequests) {
        this.vacationrequests = vacationrequests;
    }
    
    public void addVacationRequest(VacationRequest req)
    {
        vacationrequests.add(req);
        req.setEmployee(this);
    }
    public void removeVacationRequest(VacationRequest req)
    {
        vacationrequests.remove(req);
        req.setEmployee(null);
    }
    public Employee(){}
    public Employee(final String fname,final String lname)
    {
        firstname=fname;        
        lastname=lname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
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
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));

    }
    
    
}
