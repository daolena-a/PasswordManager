package org.passmanager.manager;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.Key;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 27, 2010
 * Time: 11:17:49 AM
 * To change this template use File | Settings | File Templates.
 */
public final class KeyEncryption {
    String password;
    Key clef;

    public KeyEncryption(final StringBuilder pass) {

        password = pass.toString();
        try {
            clef = new SecretKeySpec(password.getBytes("UTF-8"), "Blowfish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] crypter(final byte[] acrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, clef);
            return cipher.doFinal(acrypt);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] decrypter(final byte[] aDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, clef);
            byte[] temp = cipher.doFinal(aDecrypt);
            return temp;

        } catch (Exception e) {
            System.out.println("Error during decryption");
            e.printStackTrace();
            return null;
        }
    }


}


