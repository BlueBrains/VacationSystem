/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.Dao;

import com.model.pojo.VacationRequest;
import com.util.ObjectAdder;
import com.util.ObjectGetter;
import com.util.ObjectRemover;
import com.util.ObjectUpdater;
import com.util.ObjectsGetter;
import com.util.TransactionExecuter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
/**
 *
 * @author abd
 */
public class VacationRequestDao 
{   
    public static String getWhereClause(VacationRequest vacationrequest,String compareOp)
    {
        return getWhereClause(vacationrequest.getStatus(), vacationrequest.getVacationtype(), vacationrequest.getInterval(), vacationrequest.getCreationdate(), compareOp);
    }
    public static String getWhereClause(VacationRequest.VacationStatus status,VacationRequest.VacationType type,Long datediff,Date creationdate,String compareOp)
    {
        if(((creationdate==null)&&(compareOp!=null)&&(!"".equals(compareOp)))||((compareOp==null)||("".equals(compareOp)))&&(creationdate!=null))
            throw new IllegalArgumentException("incompatible arguments creationdate and compare operation, either both are null or both are set");
        if((status==null)&&(type==null)&&(datediff==null)&&(creationdate==null))
            return "";
        String whereclause="where ";
        String creationdateformat=null;
        if(creationdate!=null)
            creationdateformat="'"+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(creationdate)+"'";
        if(status!=null)
        {
            whereclause+="status="+status.name();
            if(type!=null)
            {
                whereclause+=" AND type="+type.name();
                if(datediff!=null)
                {
                    whereclause+=" AND time_to_sec(timediff(enddate,begindate))>="+datediff;
                    if(creationdateformat!=null)
                        whereclause+=" AND creationdate"+compareOp+creationdateformat;
                }
                else if(creationdateformat!=null)
                    whereclause+=" AND creationdate"+compareOp+creationdateformat;
            }
            else if(datediff!=null)
            {
                whereclause+=" AND time_to_sec(timediff(enddate,begindate))>="+datediff;
                if(creationdateformat!=null)
                    whereclause+=" AND creationdate"+compareOp+creationdateformat;
            }
            else if(creationdateformat!=null)
                whereclause+=" AND creationdate"+compareOp+creationdateformat;                
        }
        else if(type!=null)
        {
            whereclause+="type="+type.name();
            if(datediff!=null)
            {
                whereclause+=" AND time_to_sec(timediff(enddate,begindate))>="+datediff;
                if(creationdateformat!=null)
                    whereclause+=" AND creationdate"+compareOp+creationdateformat;
            }
            else if(creationdateformat!=null)
                whereclause+=" AND creationdate"+compareOp+creationdateformat;
        }
        else if(datediff!=null)
        {
            whereclause+="time_to_sec(timediff(enddate,begindate))>="+datediff;
            if(creationdateformat!=null)
                whereclause+=" AND creationdDate"+compareOp+creationdateformat;
        }
        else
            whereclause+="creationdate"+compareOp+creationdateformat;
        return whereclause;
    }
    public static void addVacationRequest(VacationRequest vacation)
    {
        new TransactionExecuter<VacationRequest,Void>().execute(new ObjectAdder<VacationRequest>(), vacation);        
    }
    public static void removeVacationRequest(VacationRequest vacation)
    {
        new TransactionExecuter<VacationRequest,Void>().execute(new ObjectRemover<VacationRequest>(), vacation);                
    }
    public static void updateVacationRequest(VacationRequest vacation)
    {
        new TransactionExecuter<VacationRequest,Void>().execute(new ObjectUpdater<VacationRequest>(), vacation);                        
    }
    public static VacationRequest getVacationRequest(Integer id)
    {
        return new TransactionExecuter<Integer,VacationRequest>().execute(new ObjectGetter<>(VacationRequest.class),id);                
    }
    public static List<VacationRequest> getVacationRequests(String whereclause,String order)
    {
        return new TransactionExecuter<String,List<VacationRequest>>().execute(new ObjectsGetter<VacationRequest>(),"FROM VacationRequest "+whereclause+" "+TransactionExecuter.getOrderClause(order));                
    }
    
}
