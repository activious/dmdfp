package dmdfp.admin;

import dmdfp.share.Cloudy;
import dmdfp.share.Environment;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by khk on 2/20/14.
 */
@ManagedBean(name="auth")
@SessionScoped
public class Authorization implements Serializable
{
    private static final String
            SUCCESS = "SUCCESS",
            DENIED = "DENIED";

    private boolean loggedIn;

    @ManagedProperty("#{user}")
    private User user;

    @ManagedProperty("#{env}")
    private Environment env;

    public String login()
    {
        try
        {
            if (Cloudy.login(user.getUsername(), user.getPassword(), env.getCloudSchemaPath()))
            {
                setLoggedIn(true);
                return SUCCESS;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return DENIED;
    }

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    public void setLoggedIn(boolean value)
    {
        loggedIn = value;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User u)
    {
        user = u;
    }

    public void setEnv(Environment e)
    {
        env = e;
    }
}
