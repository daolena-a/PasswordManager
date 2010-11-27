package org.passmanager.interfaces;

import objects.PasswordLogin;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:11:47 PM
 * To change this template use File | Settings | File Templates.
 */
public interface DaoPasswords {
    public void savePassword(PasswordLogin pl);
    public PasswordLogin  getLoginPassword(String label) ;
    public List<PasswordLogin> getAllPassword();
}
