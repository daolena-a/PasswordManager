package org.passmanager.ui;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 20, 2010
 * Time: 10:21:31 AM
 * To change this template use File | Settings | File Templates.
 */
public class Authenticator {

    public static boolean checkPass(char[] p) {
        if (p.length == 8 && p[0] == 'a') {
            return true;
        }
        throw new IllegalArgumentException("bad pass");
    }
}
