package org.passmanager.manager;

import objects.EncryptedPasswordLogin;
import objects.PasswordLogin;
import org.passmanager.interfaces.DaoPasswords;
import org.passmanager.interfaces.EncryptionManager;


import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:30:26 PM
 * To change this template use File | Settings | File Templates.
 */
public class DaoPasswordImpl implements DaoPasswords {
    Map<String, EncryptedPasswordLogin> db;
    EncryptionManager em ;
    public DaoPasswordImpl (){
        em = (EncryptionManager) new EncryptionManagerImpl();
        setDb();
    }
    public void savePassword(PasswordLogin pl) {
        //To change body of implemented methods use File | Settings | File Templates.

        byte[] encryptedPassword = em.rsaEncrypt(pl.getPassword().getBytes());
        byte[] encryptedLogin = em.rsaEncrypt(pl.getLogin().getBytes());
        db.put(pl.getLabel(), new EncryptedPasswordLogin(encryptedLogin,encryptedPassword));
        System.out.println ("going to be written :"+pl.getLabel() + new String(encryptedLogin) +" \n"+new String (encryptedPassword));
        saveDb();

    }

    public PasswordLogin  getLoginPassword(String label) {
        String log = new String (em.rsaDecrypt(db.get(label).getLogin()));
        String pass = new String(em.rsaDecrypt(db.get(label).getPassword()));

        return new PasswordLogin(log,pass,label);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public List<PasswordLogin> getAllPassword() {
        //for (PasswordLogin pl : bd.)
        List<PasswordLogin> result = new ArrayList();


        if (db != null){
            for (Map.Entry<String,EncryptedPasswordLogin> entry :db.entrySet()){
                result.add(new PasswordLogin(
                                new String (em.rsaDecrypt(entry.getValue().getLogin())),
                                new String (em.rsaDecrypt(entry.getValue().getPassword())),
                                entry.getKey())
                        );
            }
        }
        //To change body of implemented methods use File | Settings | File Templates.
        return result;
    }
    private void setDb(){
        FileInputStream  fis = null;
        File data = null;
        try {
            data = new File("/home/adrien/Desktop/db.data");
            if (!data.exists()){
                data.createNewFile();
                db = new HashMap<String,EncryptedPasswordLogin>();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        synchronized (this){
            try{
                if (data.length()<1){
                    db = new HashMap<String,EncryptedPasswordLogin>();
                    return;
                }
                if (data == null){
                    throw new IllegalArgumentException("Problème dans la création de la base de donnée");
                }
                fis = new FileInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Map<String,EncryptedPasswordLogin> tempdbObject = null; 
               if ((tempdbObject =  (Map <String,EncryptedPasswordLogin>) ois.readObject()) != null){
                   db = tempdbObject;


                }

                else {
                   db = new HashMap<String,EncryptedPasswordLogin>();
                   return;
                }
                System.out.println("Lu");
                fis.close();
                ois.close();

            }catch (FileNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ClassNotFoundException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }

    private void saveDb (){

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            synchronized (this){
                fos = new FileOutputStream("/home/adrien/Desktop/db.data");
                oos = new ObjectOutputStream(fos);
                oos.writeObject(db);
                oos.flush();
                System.out.println("Ecrit");
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                fos.close();
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

        }
    }
}
