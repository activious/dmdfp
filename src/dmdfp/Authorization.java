package dmdfp;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by khk on 2/20/14.
 */
@ManagedBean(name="auth")
@SessionScoped
public class Authorization
{
    private boolean loggedIn;

    public boolean isLoggedIn()
    {
        return loggedIn;
    }

    // Test
    public void setLoggedIn(boolean value)
    {
        loggedIn = value;
    }
}
