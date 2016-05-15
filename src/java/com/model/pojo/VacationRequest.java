/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.EntityListeners;
import static javax.persistence.EnumType.STRING;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
/**
 *
 * @author abd
 */

@Entity
public class VacationRequest implements Serializable 
{
    public static enum VacationType
    {
        Holiday
    }
    public static enum VacationStatus
    {
        UnKnown,
        AcceptedByDivisionManagers,
        Accepted,
        RejectedByDivisionManager,
        RejectedByCompanyManager
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    
    @Basic(optional=false)
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date creationdate;
    
    @Basic(optional=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date begindate;
    
    @Basic(optional=false)
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date enddate;
    
    @Basic(optional=false)
    @Enumerated(STRING)
    private VacationStatus status;

    @Basic(optional=false)
    @Enumerated(STRING)
    private VacationType vacationtype;
    
    public VacationRequest() 
    {
        status=VacationStatus.UnKnown;
    }

    public VacationRequest(Date begindate, Date enddate, VacationType vacationtype, Employee employee) 
    {
        this.begindate = begindate;
        this.enddate = enddate;
        this.vacationtype = vacationtype;
        this.employee = employee;
        status=VacationStatus.UnKnown;
    }
    
    public VacationType getVacationtype() {
        return vacationtype;
    }

    public void setVacationtype(VacationType vacationtype) {
        this.vacationtype = vacationtype;
    }
        
    @ManyToOne(cascade={CascadeType.REFRESH})
    @JoinColumn(nullable=false)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
    
   

    public Date getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(Date creationdate) {
        this.creationdate = creationdate;
    }

    public Date getBegindate() {
        return begindate;
    }

    public void setBegindate(Date begindate) {
        this.begindate = begindate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public VacationStatus getStatus() {
        return status;
    }

    public void setStatus(VacationStatus status) {
        this.status = status;
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
        if (!(object instanceof VacationRequest)) {
            return false;
        }
        VacationRequest other = (VacationRequest) object;
        return !((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id)));

    }
    public Long getInterval()
    {
        if(enddate==null)
        {
            if(begindate==null)
                return null;
            else
                return -begindate.getTime();
        }
        else if(begindate==null)
            return enddate.getTime();
        else
            return enddate.getTime()-begindate.getTime();
    }
    
    
}

