/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.bean;

import com.model.Dao.EmployeeDao;
import com.model.Dao.VacationRequestDao;
import com.model.pojo.CompanyManager;
import com.model.pojo.DivisionManager;
import com.model.pojo.Employee;
import com.model.pojo.VacationRequest;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.apache.shiro.SecurityUtils;
import org.primefaces.context.RequestContext;
import org.primefaces.push.EventBus;
import org.primefaces.push.EventBusFactory;

/**
 *
 * @author molham
 */
@ManagedBean(name="vacationManagedBean", eager=true)
@ViewScoped
public class VacationManagedBean {
    private Employee registeredUser;
    private VacationRequest v;
    private String employeeType;
    private List<VacationRequest> vacationRequestsList;
    private List<VacationRequest> employeesRequestsList;
    private EventBus eventBus;
//    public void onDateEndSelect(SelectEvent event) {
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
//        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
//    }
//     
    public void click() {
        RequestContext requestContext = RequestContext.getCurrentInstance();
         
        requestContext.update("form:display");
        requestContext.execute("PF('dlg').show()");
    }    
    
    public List<VacationRequest> getEmployeesRequestsList() {
        return employeesRequestsList;
    }

    public void setEmployeesRequestsList(List<VacationRequest> employeesRequestsList) {
        this.employeesRequestsList = employeesRequestsList;
    }

    public Employee getRegisteredUser() {
        return registeredUser;
    }

    public void setRegisteredUser(Employee registeredUser) {
        this.registeredUser = registeredUser;
    }

    public VacationRequest getV() {
        return v;
    }

    public void setV(VacationRequest v) {
        this.v = v;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public List<VacationRequest> getVacationRequestsList() {
        return vacationRequestsList;
    }

    public void setVacationRequestsList(List<VacationRequest> vacationRequestsList) {
        this.vacationRequestsList = vacationRequestsList;
    }
    
    public VacationManagedBean() {
        String username = (String) SecurityUtils.getSubject()
                .getSession().getAttribute("username");
        registeredUser = EmployeeDao.getEmployee(username);
        employeeType = registeredUser.getClass().getSimpleName();
        v = new VacationRequest();
        
    }
    
    @PostConstruct
    public void init()
    {
        EmployeeDao.initializeVacationRequests(registeredUser);
        vacationRequestsList = registeredUser.getVacationrequests();
        if(registeredUser instanceof DivisionManager){            
            employeesRequestsList = VacationRequestDao.getVacationRequestsWithDivisions(
                    "where d.id='"+registeredUser.getDivision().getId()+
                    "' and e.id <> '"+registeredUser.getId()+"'",null);
            eventBus=EventBusFactory.getDefault().eventBus();
        }else if(registeredUser instanceof CompanyManager){
            employeesRequestsList = VacationRequestDao.getVacationRequestsWithEmployee(" where v.status not in ( '"+
                    VacationRequest.VacationStatus.UnKnown+"', '"+
                    VacationRequest.VacationStatus.RejectedByDivisionManager+"' )",null);        
//            List<VacationRequest> DivisionManagerReq = VacationRequestDao.getVacationRequestsWithEmployee(
//                    "where e.type = '"+DivisionManager.class.getSimpleName()
//                    +"' ",null);
//            employeesRequestsList.addAll(DivisionManagerReq);
            eventBus=EventBusFactory.getDefault().eventBus();

            List<VacationRequest> DivisionManagerReq = VacationRequestDao.getVacationRequestsWithDivisions(
                    "where d.divisionmanager = e ",null);
            employeesRequestsList.addAll(DivisionManagerReq);
        }
        
    }    
    
    public String addVacationRequest(){
        v.setStatus(VacationRequest.VacationStatus.UnKnown);
        registeredUser.addVacationRequest(v);        
        VacationRequestDao.addVacationRequest(v);
        return "employee_home.xhtml?faces-redirect=true";
    }
    private String acceptmessage(Employee emp,VacationRequest v)
    {
        if(emp instanceof DivisionManager)
        {
            return "تم قبول طلب الإجازة رقم "+v.getId()+" من قبل مدير قسمك";
        }
        else if(emp instanceof CompanyManager)
        {
            return "تم قبول طلب الإجازة رقم "+v.getId()+" من قبل مدير الشركة";
        }
        return null;
    }
    private String rejectmessage(Employee emp,VacationRequest v)
    {
        if(emp instanceof DivisionManager)
        {
            return "تم رفض طلب الإجازة رقم "+v.getId()+" من قبل مدير قسمك";
        }
        else if(emp instanceof CompanyManager)
        {
            return "تم رفض طلب الإجازة رقم "+v.getId()+" من قبل مدير الشركة";
        }
        return null;
    }
    public void acceptVacationRequest(VacationRequest v){
        if(registeredUser instanceof DivisionManager)        
        {
            v.setStatus(VacationRequest.VacationStatus.AcceptedByDivisionManager);
        }
        else 
            v.setStatus(VacationRequest.VacationStatus.Accepted);
        VacationRequestDao.updateVacationRequest(v);
        eventBus.publish("/"+v.getEmployee().getUsername(), new FacesMessage(acceptmessage(registeredUser, v)));
    }
    
    public void rejectVacationRequest(VacationRequest v){
        if(isDivisionManager())
            v.setStatus(VacationRequest.VacationStatus.RejectedByDivisionManager);
        else 
            v.setStatus(VacationRequest.VacationStatus.RejectedByCompanyManager);    
        VacationRequestDao.updateVacationRequest(v);
        eventBus.publish("/"+v.getEmployee().getUsername(), new FacesMessage(rejectmessage(registeredUser, v)));
    }
    
    public boolean isCompanyManager(){
        return  registeredUser instanceof CompanyManager;
    }
    
    public boolean isDivisionManager(){
        return  registeredUser instanceof DivisionManager;
    }    
    
    public String addAction(){        
        return "new_vacationRequest.xhtml?faces-redirect=true";    
    }
    public String getStatus(VacationRequest ve) {
        return ve.getStatus().toString();
    }
}
