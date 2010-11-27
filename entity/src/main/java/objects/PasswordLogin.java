package objects;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:17:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordLogin {
    String login,password,label;
    public PasswordLogin(){
        login = "";
        password = "";
        label = "";
    }
    public PasswordLogin(String log, String pass){
        login = log;
        password = pass;

    }
     public PasswordLogin(String log, String pass, String lab){
        login = log;
        password = pass;
         label = lab;

    }
    public String getLogin(){
        return login;
    }
    public String getPassword(){
        return password;
    }
    public String getLabel(){
        return label;
    }
    public String toString (){
        return "- "+label+" = "+login +" + "+ password;
    }
}
