package org.passmanager.interfaces;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:09:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface EncryptionManager {
     public byte[] rsaEncrypt(byte[] data) ;
     public byte[] rsaDecrypt(byte[] data);
}
