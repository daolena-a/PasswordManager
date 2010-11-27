package org.passManager;

import objects.PasswordLogin;
import org.passmanager.interfaces.DaoPasswords;
import org.passmanager.manager.DaoPasswordImpl;

import java.io.Console;
import java.util.List;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 20, 2010
 * Time: 11:51:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class Launcher {
    public static void main (String[] args){
        System.out.println ("MENU");
        Scanner sc = new Scanner(System.in);
        String passs = sc.next();

        char[] pass = passs.toCharArray();

        System.out.println ("##Menu##");
        System.out.println ("1) new log");
        System.out.println ("2) show all");
        int menuEntry = sc.nextInt();
        DaoPasswords dp = new DaoPasswordImpl();
        switch (menuEntry){
            case 1:
                System.out.println ("label? ");
                String label2save = sc.next();
                System.out.println ("log? ");
                String log2save = sc.next();
                System.out.println ("pass? ");
                String  password2save = sc.next();
                dp.savePassword(new PasswordLogin(log2save,password2save,label2save));
                break;
            case 2:
                List<PasswordLogin> all = dp.getAllPassword();
                for (PasswordLogin currentPl : all){
                    System.out.println (currentPl.toString());
                }
                break;
        }
            


         sc.close();
        //org.passmanager.ui.Authenticator.checkPass(pass);



     }
}
