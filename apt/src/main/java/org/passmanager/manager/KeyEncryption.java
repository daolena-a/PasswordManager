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
public final class  KeyEncryption {
    // private String algo;
    String password;
    Key clef;
    public  KeyEncryption ( final StringBuilder pass) {

        password = pass.toString();
        try{
            clef = new SecretKeySpec(password.getBytes("UTF-8"),"Blowfish");
        }
        catch (Exception E){
            E.printStackTrace();
        }
    }
    public byte[] crypter(final String acrypt) {


        try {

            //set information we need before crypt the string
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE,clef);
            //crypt
            return cipher.doFinal(acrypt.getBytes());

        }
        catch (Exception e) {
            return null;
        }


    }

    public String decrypter(final byte[] aDecrypt) {
        try {
            //set information before crypt
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE,clef);
            //decrypt
            byte[] temp = cipher.doFinal(aDecrypt);
            //create a string to store the string get by decryption
            String res = new String (temp);
            return res;

        }
        catch (Exception e) {
            System.out.println("Erreur lors du décryptage des donnees");
            e.printStackTrace();
            return null;
        }
    }


}


