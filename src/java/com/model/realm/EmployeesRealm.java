/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.realm;

import com.model.Dao.EmployeeDao;
import com.model.pojo.Employee;
import com.util.EmployeeAuthInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SaltedAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;

/**
 *
 * @author abd
 */
public class EmployeesRealm extends JdbcRealm
{
    @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    // identify account to log to
    UsernamePasswordToken userPassToken = (UsernamePasswordToken) token;
    final String username = userPassToken.getUsername();

    if (username == null) {
      System.out.println("Username is null.");
      return null;
    }

    // read password hash and salt from db
    final Employee employee = EmployeeDao.getEmployee(username);

    if (employee == null) {
      System.out.println("No account found for user [" + username + "]");
      return null;
    }

    // return salted credentials
    SaltedAuthenticationInfo info = new EmployeeAuthInfo(username, employee.getPasswordhash(), employee.getPasswordsalt());

    return info;
  }
}

