package objects;

/**
 * Created by IntelliJ IDEA.
 * User: adrien
 * Date: Nov 18, 2010
 * Time: 10:17:07 PM
 * To change this template use File | Settings | File Templates.
 */
public class PasswordLogin {
    String login, password, label;

    public PasswordLogin() {
        login = "";
        password = "";
        label = "";
    }

    public PasswordLogin(String log, String pass) {
        login = log;
        password = pass;

    }

    public PasswordLogin(String log, String pass, String lab) {
        login = log;
        password = pass;
        label = lab;

    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getLabel() {
        return label;
    }

    public void setLogin(String log) {
        login = log;
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public String toString() {
        return "- " + label + " = " + login + " + " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PasswordLogin that = (PasswordLogin) o;

        if (label != null ? !label.equals(that.label) : that.label != null) return false;


        return true;
    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (label != null ? label.hashCode() : 0);
        return result;
    }
}
