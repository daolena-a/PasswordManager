package org.passmanager.manager;

import org.passmanager.interfaces.EncryptionManager;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:26:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class EncryptionManagerImpl  implements EncryptionManager {
    KeyEncryption keyEncryptpionManager ;
   public EncryptionManagerImpl (String passwordKey){
       keyEncryptpionManager = new KeyEncryption(new StringBuilder( passwordKey));
       generateKey();
       System.out.println("taktak");
   }
   private void generateKey() {
        try {
             File keyPrev = new File("key.prv");
            File keyPub = new File("key.pub");
             if (!keyPrev.exists() && !keyPub.exists()){
            if (!keyPrev.exists()){
                keyPrev.createNewFile();
            } if (!keyPub.exists()){
                keyPrev.createNewFile(); 
            }
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, new SecureRandom());
            System.out.println("kpg initialize");
            KeyPair keys = kpg.generateKeyPair();
            System.out.println("keys genereted");
            RSAPrivateKey privateK = (RSAPrivateKey) keys.getPrivate();
            RSAPublicKey publicK = (RSAPublicKey) keys.getPublic();



             System.out.println("Writing them into files...");
            FileOutputStream fpriv = new FileOutputStream("key.prv");
            fpriv.write(privateK.getEncoded());
            fpriv.close();
            System.out.println("private key written");
            FileOutputStream fpub = new FileOutputStream("key.pub");
            fpub.write(publicK.getEncoded());
            fpub.close();
            System.out.println("public key written");

             }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private RSAPublicKey getKey() {
        FileInputStream pubfile = null;
        RSAPublicKey pubkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pubfile = new FileInputStream("key.pub");
            byte[] byte_pub = new byte[pubfile.available()];
            pubfile.read(byte_pub);
            X509EncodedKeySpec pubspec = new X509EncodedKeySpec(byte_pub);
            pubkey = (RSAPublicKey) factory.generatePublic(pubspec);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pubfile.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return pubkey;

    }
     private RSAPrivateKey getKeyPrivate() {
        FileInputStream prvfile = null;
        RSAPrivateKey prvkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            prvfile = new FileInputStream("key.prv");
            byte[] byte_prv = new byte[prvfile.available()];
            prvfile.read(byte_prv);
            PKCS8EncodedKeySpec prvspec = new PKCS8EncodedKeySpec(byte_prv);
            prvkey = (RSAPrivateKey) factory.generatePrivate(prvspec);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                prvfile.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return prvkey;

    }

    public byte[] rsaEncrypt(byte[] data) {
        RSAPublicKey publicKey = getKey();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
             cipherData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }
        public byte[] rsaDecrypt(byte[] data) {
        RSAPrivateKey privateKey = getKeyPrivate();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
             cipherData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }
      
}
