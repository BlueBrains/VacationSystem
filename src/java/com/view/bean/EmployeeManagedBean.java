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
import com.util.HibernateUtil;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.apache.shiro.SecurityUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
/**
 *
 * @author molham
 */

@ManagedBean(name="employeeManagedBean", eager=true)
@SessionScoped
public class EmployeeManagedBean {
 
    /** Creates a new instance of EmployeeManagedBean */

    private Employee e;
    private String passwordplain;    
    private String divisionName;    
    private List<Employee> employeeList;
    private String employeeType;    

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeetype) {
        this.employeeType = employeetype;
    }
    
    
    public String getPasswordplain() {
        return passwordplain;
    }

    public void setPasswordplain(String passwordplain) {
        this.passwordplain = passwordplain;
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
    }
    
    @PostConstruct
    public void init()
    {
        employeeList=EmployeeDao.getEmployees("");
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestParameterMap();                
        if(params.get("id")!=null){
            e = EmployeeDao.getEmployee(Integer.parseInt(params.get("id")));
            divisionName = e.getDivision().getName();
            if(e instanceof ManagementEmployee){
                employeeType = "managment employee";
            }else if(e instanceof DivisionManager){
                employeeType = "manager";
            }else if(e instanceof Employee){            
                    employeeType = "employee";
            }
        }
    }      
    
    public void addEmployee()
    {
        Division ed = DivisionDao.getDivisionsByName(divisionName, null).get(0);
        e.setDivision(ed);        
        Employee fe;
        switch(employeeType){
            case "company manager": 
            {
                fe = new CompanyManager(e);     
                e.setDivision(null);
            }
                break;            
            case "manager": 
            {
                fe = new DivisionManager(e);
                DivisionDao.initializeEmployees(ed);
                Employee lastManager = ed.getDivisionmanager();
                if(lastManager!=null){                    
                    Employee normalEmp = new Employee(lastManager);
                    updateEmployeeType(normalEmp);
                    ed.setDivisionmanager(null);
                    DivisionDao.updateDivision(ed);
                }
                ed.setDivisionmanager((DivisionManager)fe);                
            }
                break;            
            case "managment employee":{
                fe = new ManagementEmployee(e);                                
            }
                break;
            default: fe = e;
        }
        EmployeeDao.addEmployee(fe,passwordplain);                
//        DivisionDao.updateDivision(ed);
        employeeList=EmployeeDao.getEmployees(null);
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("employees.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(EmployeeManagedBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public void deleteEmployee(Employee eDel)
    {        
        if(eDel instanceof DivisionManager){
            eDel.getDivision().setDivisionmanager(null);
            DivisionDao.updateDivision(eDel.getDivision());
        }
        EmployeeDao.removeEmployee(eDel);        
        employeeList=EmployeeDao.getEmployees(null);
    }
    
    public String getCurrentEmployee()
    {
        return (String)SecurityUtils.getSubject().getSession().getAttribute("username");
    }
    
    public String updateEmployee()
    {
        Division ed = DivisionDao.getDivisionsByName(divisionName, null).get(0);
        e.setDivision(ed);
        Employee updatedEmp;
        switch(employeeType){
            case "manager": {
                updatedEmp = new DivisionManager(e);
                DivisionDao.initializeEmployees(ed);
                Employee lastManager = ed.getDivisionmanager();
                if(lastManager!=null && !lastManager.equals(e)){                    
                    Employee normalEmp = new Employee(lastManager);
                    updateEmployeeType(normalEmp);
                    ed.setDivisionmanager(null);
                    DivisionDao.updateDivision(ed);
                }
                ed.setDivisionmanager((DivisionManager)updatedEmp);                
            }
                break;            
                
            case "managment employee":{
                updatedEmp = new ManagementEmployee(e);  
                if(e instanceof DivisionManager){
                    Division tmp = e.getDivision();
                    DivisionDao.initializeEmployees(tmp);
                    tmp.setDivisionmanager(null);
                    DivisionDao.updateDivision(tmp);
                }                
            }
                break;
                
            default: {
                updatedEmp = new Employee(e);
                if(e instanceof DivisionManager){
                    Division tmp = e.getDivision();
                    DivisionDao.initializeEmployees(tmp);
                    tmp.setDivisionmanager(null);
                    DivisionDao.updateDivision(tmp);
                }                
            }
        }        
        EmployeeDao.updateEmployee(updatedEmp);
        updateEmployeeType(updatedEmp);
//        DivisionDao.updateDivision(ed);
        employeeList=EmployeeDao.getEmployees(null);
        return "employees.xhtml?faces-redirect=true";
    }
    
    public void updateEmployeeType(Employee emp){
        Session s = HibernateUtil.getSessionFactory().openSession();
        String query = "Update Employee set DTYPE ='"+
                emp.getClass().getSimpleName()+
                "' where id = '"+
                emp.getId()+"'";        
        try {
            s.getTransaction().begin();
            s.createSQLQuery(query).executeUpdate();
            s.getTransaction().commit();
            s.close();
        }
        catch (HibernateException erro){
            s.getTransaction().rollback();
            s.close();
        }        
    }
    public String getEmployeeType(Employee emp){
        return emp.getClass().getSimpleName();
    }
    public String addAction() {
            return "add_employee.xhtml?faces-redirect=true";
    }        
    public String editAction(Employee emp) {
//            emp.setEditable(true);
//            divisionName = emp.getDivision().getName();
            return "edit_employee.xhtml?faces-redirect=true&id="+emp.getId();
    }        
    
    public boolean isCompanyHasManager()
    {
        for(Employee emp: employeeList){
            if(emp instanceof CompanyManager)
                return true;
        }
        return false;
    }
}