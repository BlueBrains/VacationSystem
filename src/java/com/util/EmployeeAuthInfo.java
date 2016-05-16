/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.util;

import com.model.Dao.EmployeeDao;
import com.model.pojo.Employee;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;

/**
 *
 * @author abd
 */
public class EmployeeAuthInfo implements SaltedAuthenticationInfo 
{
    private static final long serialVersionUID = -5467967895187234984L;

    private final String username;
    private final String password;
    private final String salt;

    public EmployeeAuthInfo(String username, String password, String salt) {
        this.username = username;
        this.password = password;
        this.salt = salt;
    }
    
    
    @Override
    public ByteSource getCredentialsSalt() 
    {
        return  new SimpleByteSource(Base64.decode(salt));
    }

    @Override
    public PrincipalCollection getPrincipals() 
    {
        return new SimplePrincipalCollection(username, username);
    }

    @Override
    public Object getCredentials() {
        return password;
    }
    
    public static Employee getCurrentEmployee()
    {
        Subject currentemp=SecurityUtils.getSubject();
        if(currentemp.isAuthenticated())
        {
            String username=(String)currentemp.getSession().getAttribute("username");
            return EmployeeDao.getEmployee(username);
        }
        else
            return null;
    }
}
