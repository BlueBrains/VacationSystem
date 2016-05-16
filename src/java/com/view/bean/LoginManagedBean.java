/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view.bean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 *
 * @author abd
 */
@ManagedBean
@RequestScoped
public class LoginManagedBean 
{
    private String username;
    private String password;
    private boolean rememberme;
    private org.apache.shiro.mgt.SecurityManager securitymanager;

    public LoginManagedBean() 
    {
        Factory<org.apache.shiro.mgt.SecurityManager> factory=new IniSecurityManagerFactory();
        securitymanager=factory.getInstance();
    }
    
    
    public boolean isRememberme() {
        return rememberme;
    }

    public void setRememberme(boolean rememberme) {
        this.rememberme = rememberme;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void login()
    {
        Subject currentemp=SecurityUtils.getSubject();
        if(!currentemp.isAuthenticated())
        {
            UsernamePasswordToken token = new UsernamePasswordToken(username,password);
            token.setRememberMe(rememberme);
            try {
                currentemp.login(token);
                System.out.println("User [" + currentemp.getPrincipal().toString() + "] logged in successfully.");

                // save current username in the session, so we have access to our User model
                currentemp.getSession().setAttribute("username", username);
                
            } 
            catch (UnknownAccountException uae) 
            {
              System.out.println("There is no user with username of "
                        + token.getPrincipal());
            } 
            catch (IncorrectCredentialsException ice) 
            {
              System.out.println("Password for account " + token.getPrincipal()
                        + " was incorrect!");
            } 
            catch (LockedAccountException lae) 
            {
              System.out.println("The account for username " + token.getPrincipal()
                        + " is locked.  "
                        + "Please contact your administrator to unlock it.");
            }
        }
    }
    public void logout()
    {
        Subject currentemp=SecurityUtils.getSubject();
        currentemp.logout();
    }
}
