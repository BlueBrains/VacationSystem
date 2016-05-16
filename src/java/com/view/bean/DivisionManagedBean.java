/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.bean;

import com.model.Dao.DivisionDao;
import com.model.Dao.EmployeeDao;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.model.pojo.Division;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
/**
 *
 * @author molham
 */

@ManagedBean(name="divisionManagedBean", eager=true)
@SessionScoped
public class DivisionManagedBean {
    private Division d;
    private List<Division> divisionList;

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

    public DivisionManagedBean() {        
        d = new Division();
        divisionList = new ArrayList<Division>();
    }
    
    @PostConstruct
    public void init()
    {        
        divisionList= DivisionDao.getDivisions(null);
    }
    
    public void addDivision(){
        DivisionDao.addDivision(d);
        divisionList = DivisionDao.getDivisions(null);
    }
    public void deleteDivision(){
        DivisionDao.removeDivision(d);
        divisionList = DivisionDao.getDivisions(null);
    }
    
    public void updateDivision(){
        DivisionDao.updateDivision(d);
        divisionList = DivisionDao.getDivisions(null);
    }
}
