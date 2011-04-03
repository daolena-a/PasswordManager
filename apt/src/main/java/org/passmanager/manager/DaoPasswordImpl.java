package org.passmanager.manager;

import objects.EncryptedPasswordLogin;
import objects.PasswordLogin;
import org.passmanager.interfaces.DaoPasswords;
import org.passmanager.interfaces.EncryptionException;
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
    EncryptionManager em;

    public DaoPasswordImpl(String password) {
        em = (EncryptionManager) new EncryptionManagerImpl(password);
        setDb();
    }

    public void removeLoginPassword(PasswordLogin pl) {
        db.remove(pl.getLabel());
        saveDb();
    }

    public void savePassword(PasswordLogin pl) {
        byte[] encryptedPassword = new byte[0];
        try {
            encryptedPassword = em.rsaEncrypt(pl.getPassword().getBytes());
            byte[] encryptedLogin = em.rsaEncrypt(pl.getLogin().getBytes());
            db.put(pl.getLabel(), new EncryptedPasswordLogin(encryptedLogin, encryptedPassword));
            saveDb();
        } catch (EncryptionException e) {
            e.printStackTrace();
        }


    }

    public PasswordLogin getLoginPassword(String label) {
        String log = null;
        try {
            log = new String(em.rsaDecrypt(db.get(label).getLogin()));

            String pass = new String(em.rsaDecrypt(db.get(label).getPassword()));
            return new PasswordLogin(log, pass, label);
        } catch (EncryptionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<PasswordLogin> getAllPassword() {
        List<PasswordLogin> result = new ArrayList();
        if (db != null) {
            for (Map.Entry<String, EncryptedPasswordLogin> entry : db.entrySet()) {
                try {
                    result.add(new PasswordLogin(
                            new String(em.rsaDecrypt(entry.getValue().getLogin())),
                            new String(em.rsaDecrypt(entry.getValue().getPassword())),
                            entry.getKey())
                    );
                } catch (EncryptionException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void setDb() {
        FileInputStream fis = null;
        File data = null;
        try {
            data = new File("db.data");
            if (!data.exists()) {
                data.createNewFile();
                db = new HashMap<String, EncryptedPasswordLogin>();
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        synchronized (this) {
            try {
                if (data.length() < 1) {
                    db = new HashMap<String, EncryptedPasswordLogin>();
                    return;
                }
                if (data == null) {
                    throw new IllegalArgumentException();
                }
                fis = new FileInputStream(data);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Map<String, EncryptedPasswordLogin> tempdbObject = null;
                if ((tempdbObject = (Map<String, EncryptedPasswordLogin>) ois.readObject()) != null) {
                    db = tempdbObject;


                } else {
                    db = new HashMap<String, EncryptedPasswordLogin>();
                    return;
                }
                System.out.println("Read");
                fis.close();
                ois.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void saveDb() {

        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            synchronized (this) {
                fos = new FileOutputStream("db.data");
                oos = new ObjectOutputStream(fos);
                oos.writeObject(db);
                oos.flush();
                System.out.println("Written");
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
