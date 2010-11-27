package objects;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:32:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class EncryptedPasswordLogin implements Serializable{
    byte[] login;
    byte[]password;
    public EncryptedPasswordLogin (byte[] log , byte[] pass){
        login = log;
        password = pass;
    }
    public byte[] getLogin(){
        return login;

    }
    public byte[] getPassword(){
        return password;
    }
}
