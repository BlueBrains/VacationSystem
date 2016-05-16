package com.view.bean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.model.Dao.DivisionDao;
import com.model.Dao.EmployeeDao;
import com.model.pojo.CompanyManager;
import com.model.pojo.Division;
import com.model.pojo.DivisionManager;
import com.model.pojo.Employee;
import com.model.pojo.ManagementEmployee;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
/**
 *
 * @author molham
 */

@ManagedBean(name="employeeManagedBean", eager=true)
@SessionScoped
public class EmployeeManagedBean {
 
    /** Creates a new instance of EmployeeManagedBean */

    private Employee e;
    private String eType;
    private String divisionName;
    private List<Employee> employeeList;

    public String geteType() {
        return eType;
    }

    public void seteType(String eType) {
        this.eType = eType;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName = divisionName;
    }

    public Employee getE() {
        return e;
    }

    public void setE(Employee e) {
        this.e = e;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }


    public EmployeeManagedBean() {
        e= new Employee();
        employeeList= new ArrayList<Employee>();        
    }
    
    @PostConstruct
    public void init()
    {
        employeeList=EmployeeDao.getEmployees("");
    }
    
    
    public String redirect2Add()
    {
        return "Add.xhtml?faces-redirect=true";
    }
    
    public String redirect2View()
    {
        employeeList= new ArrayList<Employee>();
        employeeList=EmployeeDao.getEmployees(null);
        return "index.xhtml?faces-redirect=true";
    }
    
    public void addEmployee()
    {
        Division ed = DivisionDao.getDivisionsByName(divisionName, null).get(0);
        e.setDivision(ed);
        Employee fe;
        switch(eType){
            case "manager": {
                fe = new DivisionManager(e);
                DivisionDao.initializeEmployees(ed);
                ed.setDivisionmanager((DivisionManager)fe);                
            }
                break;            
            case "managment employee":{
                fe = new ManagementEmployee(e);                                
            }
                break;
            default: fe = e;
        }
        EmployeeDao.addEmployee(fe);                
        DivisionDao.updateDivision(ed);
        employeeList=EmployeeDao.getEmployees(null);
    }
    
    
    public void deleteEmployee()
    {
        Employee eDel = EmployeeDao.getEmployee(e.getId());;        
        EmployeeDao.removeEmployee(eDel);        
        employeeList=EmployeeDao.getEmployees(null);
    }
    
    
    public void updateEmployee()
    {
        EmployeeDao.updateEmployee(e);
        employeeList= new ArrayList<Employee>();
        employeeList=EmployeeDao.getEmployees(null);        
    }


}