package com.view.bean;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.model.Dao.DivisionDao;
import com.model.Dao.EmployeeDao;
import com.model.pojo.Division;
import com.model.pojo.DivisionManager;
import com.model.pojo.Employee;
import com.model.pojo.ManagementEmployee;
import com.util.HibernateUtil;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.hibernate.Session;
/**
 *
 * @author molham
 */

@ManagedBean(name="employeeManagedBean", eager=true)
@ViewScoped
public class EmployeeManagedBean {
 
    /** Creates a new instance of EmployeeManagedBean */

    private Employee e;
    private String passwordplain;
    private String eType;
    private String divisionName;    
    
    
    public String geteType() {
        return eType;
    }
    private List<Employee> employeeList;

    private String employeetype;

    public String getEmployeetype() {
        return employeetype;
    }

    public void setEmployeetype(String employeetype) {
        this.employeetype = employeetype;
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
                eType = "managment employee";
            }else if(e instanceof DivisionManager){
                eType = "manager";
            }else if(e instanceof Employee){            
                    eType = "employee";
            }
        }
    }      
    
    public String addEmployee()
    {
        Division ed=null;
        if((divisionName!=null)&&(!"".equals(divisionName)))
        {
            ed = DivisionDao.getDivisionsByName(divisionName, null).get(0);
            e.setDivision(ed);
        }
        Employee fe;
        switch(employeetype){
            case "manager": 
            {
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
        EmployeeDao.addEmployee(fe,passwordplain);                
        //DivisionDao.updateDivision(ed);
        employeeList=EmployeeDao.getEmployees(null);
        return "employees.xhtml?faces-redirect=true";
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
    
    
    public String updateEmployee()
    {
        Division ed = DivisionDao.getDivisionsByName(divisionName, null).get(0);
        e.setDivision(ed);
        Employee updatedEmp;
        switch(eType){
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
        DivisionDao.updateDivision(ed);
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
}