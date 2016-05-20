/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.bean;

import com.model.Dao.DivisionDao;
import com.model.Dao.EmployeeDao;
import javax.faces.bean.ManagedBean;
import com.model.pojo.Division;
import com.model.pojo.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
/**
 *
 * @author molham
 */

@ManagedBean(name="divisionManagedBean", eager=true)
@ViewScoped
public class DivisionManagedBean {
    private Division d;
    private List<Division> divisionList;
    private List<Employee> employeeList;

    public Division getD() {
        return d;
    }

    public void setD(Division d) {
        this.d = d;
    }

    public List<Division> getDivisionList() {
        return divisionList;
    }

    public void setDivisionList(List<Division> divisionList) {
        this.divisionList = divisionList;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public DivisionManagedBean() {        
        d = new Division();
        divisionList = new ArrayList<Division>();
    }
    
    @PostConstruct
    public void init()
    {        
        divisionList= DivisionDao.getDivisions(null);
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();                
        if(params.get("id")!=null){
            d = DivisionDao.getDivision(Integer.parseInt(params.get("id")));
            employeeList = EmployeeDao.getEmployeesByDivision(d.getName(), null);
        }
    }
    
    public String addDivision(){
        DivisionDao.addDivision(d);
        divisionList = DivisionDao.getDivisions(null);
        return "divisions.xhtml?faces-redirect=true";
    }
    public void deleteDivision(Division dep){
        DivisionDao.removeDivision(dep);
        divisionList = DivisionDao.getDivisions(null);
    }
    
    public String updateDivision(){
        DivisionDao.updateDivision(d);
        divisionList = DivisionDao.getDivisions(null);
        return "divisions.xhtml?faces-redirect=true";
    }
    
    public String addAction() {
            return "add_department.xhtml?faces-redirect=true";
    }
    
    public String editAction(Division dep) {
            return "edit_department.xhtml?faces-redirect=true&id="+dep.getId();
    }    
    
    public String editEmployeeAction(Employee emp) {
            return "edit_employee.xhtml?faces-redirect=true&id="+emp.getId();
    }            
    
    public String getEmployeeType(Employee emp){
        return emp.getClass().getSimpleName();
    }
        
}
