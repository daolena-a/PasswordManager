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
    public static void main(String[] args) {
        System.out.println("MENU");

        Scanner sc = new Scanner(System.in);
        //String passs = sc.next();
        Console c = System.console();
        char[] pass = c.readPassword();
        String passs = new String(pass);
        //char[] pass = passs.toCharArray();

        System.out.println("##Menu##");
        System.out.println("1) new log");
        System.out.println("2) show all");
        System.out.println("3) update");
        System.out.println("4) Remove");
        int menuEntry = sc.nextInt();
        DaoPasswords dp = new DaoPasswordImpl(passs);
        switch (menuEntry) {
            case 1:
                System.out.println("label? ");
                String label2save = sc.next();
                System.out.println("log? ");
                String log2save = sc.next();
                System.out.println("pass? ");
                //String  password2save = sc.next();

                char[] pass2save = c.readPassword();
                String password2save = new String(pass2save);
                dp.savePassword(new PasswordLogin(log2save, password2save, label2save));
                break;
            case 2:
                List<PasswordLogin> all = dp.getAllPassword();
                for (PasswordLogin currentPl : all) {
                    System.out.println(currentPl.toString());
                }
                break;
            case 3:
                List<PasswordLogin> allPass = dp.getAllPassword();
                int count = 0;
                for (PasswordLogin currentPl : allPass) {
                    System.out.println(count + " " + currentPl.getLabel() + " " + currentPl.getLogin());
                    count++;
                }
                int countEntry = sc.nextInt();
                PasswordLogin pl = allPass.get(countEntry);
                boolean valueOk = false;
                do {

                    System.out.println("log? ");
                    String log2update = sc.next();

                    System.out.println("pass? ");
                    char[] pass2update = c.readPassword();
                    if (log2update != null && log2update.length() > 0 && pass2update != null && pass2update.length > 0) {
                        valueOk = true;
                        pl.setLogin(log2update);
                        pl.setPassword(new String(pass2update));
                    }
                    dp.savePassword(pl);
                } while (valueOk == false);
                break;
            case 4:
                List<PasswordLogin> allRMPass = dp.getAllPassword();
                int countrm = 0;
                for (PasswordLogin currentPl : allRMPass) {
                    System.out.println(countrm + " " + currentPl.getLabel() + " " + currentPl.getLogin());
                    countrm++;

                }
                int countRmEntry = 0;
                countRmEntry = sc.nextInt();
                PasswordLogin pl2remove = allRMPass.get(countRmEntry);
                dp.removeLoginPassword(pl2remove);

        }


        sc.close();
        //org.passmanager.ui.Authenticator.checkPass(pass);


    }
}
