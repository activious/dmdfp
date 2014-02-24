package dmdfp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

/**
 * Created by khk on 2/20/14.
 */
@ManagedBean
@SessionScoped
public class User implements Serializable
{
    private static final String
            SUCCESS = "SUCCESS",
            DENIED = "DENIED";

    private String username;
    private String password;

    public String login()
    {
        try
        {
            if (Cloudy.login(username, password, Schema.PATH))
            {
                Authorization auth = new Authorization();
                auth.setLoggedIn(true);
                HttpSession sess = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
                sess.setAttribute("auth", auth);
                return SUCCESS;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return DENIED;
    }

    public String logout()
    {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return SUCCESS;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String value)
    {
        username = value;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String value)
    {
        password = value;
    }
}
