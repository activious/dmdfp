package dmdfp.admin;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;

/**
 * Created by khk on 2/20/14.
 */
@ManagedBean
@SessionScoped
public class User implements Serializable
{
    private String username;
    private String password;

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
